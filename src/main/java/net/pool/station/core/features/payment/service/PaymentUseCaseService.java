package net.pool.station.core.features.payment.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.configuration.mapper.MyObjectMapper;
import net.pool.station.core.bootstrap.enums.EPaymentMethod;
import net.pool.station.core.bootstrap.enums.EPaymentType;
import net.pool.station.core.bootstrap.rest.response.PayOsResponse;
import net.pool.station.core.bootstrap.utils.MyPaymentEncryptionUtils;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.match.making.MatchMaking;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlot;
import net.pool.station.core.domain.payment.Payment;
import net.pool.station.core.domain.payment.PaymentUseCase;
import net.pool.station.core.domain.transaction.Transaction;
import net.pool.station.core.domain.transaction.TransactionUseCase;
import net.pool.station.core.domain.wallet.Wallet;
import net.pool.station.core.domain.wallet.WalletUseCase;
import net.pool.station.core.features.payment.repository.feign.PaymentPlaceHolder;
import net.pool.station.core.features.payment.repository.feign.models.PaymentRequest;
import net.pool.station.core.features.payment.repository.feign.models.PaymentResponse;
import net.pool.station.core.features.payment.repository.feign.models.PaymentResponseMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentUseCaseService implements PaymentUseCase {
    PaymentPlaceHolder paymentPlaceHolder;

    TransactionUseCase transactionUseCase;

    AccountUseCase accountUseCase;

    WalletUseCase walletUseCase;

    PaymentResponseMapper mapper;

    @NonFinal
    @Value("${environment.payOs.cancelUrl}")
    String cancelUrl;

    @NonFinal
    @Value("${environment.payOs.returnUrl}")
    String returnUrl;

    @NonFinal
    @Value("${environment.deposit.percent:30}")
    Integer deposit;

    @Override
    @Transactional
    public Payment create(Payment payment) {
        LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                .orElseThrow(MyAuthenticationException::new);
        Account account = accountUseCase.findByEmail(DomainKey.of(loginInfo.email()))
                .orElseThrow(MyResourceNotFoundException::new);
        Wallet wallet = walletUseCase.findByAccountId(DomainKey.of(account.accountId()))
                .orElseThrow(MyResourceNotFoundException::new);
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderCode(System.currentTimeMillis())
                .amount(payment.amount())
                .description(payment.description())
                .buyerName(account.username())
                .buyerEmail(account.email())
                .buyerPhone(account.phone())
                .items(List.of(
                        PaymentRequest.ItemRequest.builder()
                                .name(payment.paymentTypeName())
                                .price(payment.amount())
                                .quantity(1)
                                .unit("Wallet")
                                .build()
                ))
                .cancelUrl(cancelUrl)
                .returnUrl(returnUrl)
                .build();
        paymentRequest = paymentRequest.withSignature(MyPaymentEncryptionUtils
                .encrypt(MyObjectMapper.convertFromObjectToString(paymentRequest.generateRawSignature())));
        PaymentResponse paymentResponse = paymentPlaceHolder.requestPayment(paymentRequest)
                .data();
        Transaction transaction = Transaction.builder()
                .walletId(wallet.walletId())
                .transactionCode(paymentResponse.orderCode())
                .bookingId(payment.bookingId())
                .amount(paymentResponse.amount())
                .currency(paymentResponse.currency())
                .paymentMethodCode(payment.paymentMethodCode())
                .paymentMethodName(payment.paymentMethodName())
                .paymentTypeCode(payment.paymentTypeCode())
                .paymentTypeName(payment.paymentTypeName())
                .build();
        transactionUseCase.save(transaction);

        return mapper.toDto(paymentResponse);
    }

    @Override
    @Transactional
    public Payment createFromBooking(Booking booking) {
        Account account = accountUseCase.findById(new DomainKey<>(Long.valueOf(booking.createdBy())))
                .orElseThrow(MyResourceNotFoundException::new);
        if (MyObjectUtils.isNotEquals(EPaymentMethod.BANK_TRANSFER.getCode(), booking.paymentMethodCode())) {
            throw new MyResourceNotValid("Booking không thanh toán bằng chuyển khoản.");
        }
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderCode(System.currentTimeMillis())
                .amount(booking.totalPrice())
                .description("Booking")
                .buyerName(account.username())
                .buyerEmail(account.email())
                .buyerPhone(account.phone())
                .items(fromBooking(booking))
                .cancelUrl(cancelUrl)
                .returnUrl(returnUrl)
                .build();
        paymentRequest = paymentRequest.withSignature(MyPaymentEncryptionUtils
                .encrypt(MyObjectMapper.convertFromObjectToString(paymentRequest.generateRawSignature())));
        PaymentResponse paymentResponse = paymentPlaceHolder.requestPayment(paymentRequest)
                .data();
        Transaction transaction = Transaction.builder()
                .bookingId(booking.bookingId())
                .walletId(booking.ownerWalletId())
                .transactionCode(paymentResponse.orderCode())
                .amount(paymentResponse.amount())
                .currency(paymentResponse.currency())
                .paymentMethodCode(booking.paymentMethodCode())
                .paymentMethodName(booking.paymentMethodName())
                .paymentTypeCode(EPaymentType.BOOKING_PAYMENT.getCode())
                .paymentTypeName(EPaymentType.BOOKING_PAYMENT.getName())
                .build();
        transactionUseCase.save(transaction);

        return mapper.toDto(paymentResponse);
    }

    @Override
    @Transactional
    public Payment createFromMatchMaking(MatchMaking matchMaking) {
        Account account = accountUseCase.findById(new DomainKey<>(Long.valueOf(matchMaking.createdBy())))
                .orElseThrow(MyResourceNotFoundException::new);
        if (MyObjectUtils.isNotEquals(EPaymentMethod.BANK_TRANSFER.getCode(),
                matchMaking.paymentMethodCode())) {
            throw new MyResourceNotValid("Sếp trận không thanh toán bằng chuyển khoản.");
        }
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderCode(System.currentTimeMillis())
                .amount(calculateDeposit(matchMaking))
                .description("Tiền cọc Match making")
                .buyerName(account.username())
                .buyerEmail(account.email())
                .buyerPhone(account.phone())
                .items(fromMatchMaking(matchMaking))
                .cancelUrl(cancelUrl)
                .returnUrl(returnUrl)
                .build();
        paymentRequest = paymentRequest.withSignature(MyPaymentEncryptionUtils
                .encrypt(MyObjectMapper.convertFromObjectToString(paymentRequest.generateRawSignature())));
        PaymentResponse paymentResponse = paymentPlaceHolder.requestPayment(paymentRequest)
                .data();
        Transaction transaction = Transaction.builder()
                .matchMakingId(matchMaking.matchMakingId())
                .walletId(matchMaking.ownerWalletId())
                .transactionCode(paymentResponse.orderCode())
                .amount(paymentResponse.amount())
                .currency(paymentResponse.currency())
                .paymentMethodCode(matchMaking.paymentMethodCode())
                .paymentMethodName(matchMaking.paymentMethodName())
                .paymentTypeCode(EPaymentType.MATCH_MAKING_DEPOSIT.getCode())
                .paymentTypeName(EPaymentType.MATCH_MAKING_DEPOSIT.getName())
                .build();
        transactionUseCase.save(transaction);

        return mapper.toDto(paymentResponse);
    }

    private Integer calculateDeposit(MatchMaking matchMaking) {
        int totalDeposit = matchMaking.totalPrice() * deposit / 100;
        int numberOfHoldingDay = matchMaking.numberOfHoldingDay();
        if (numberOfHoldingDay > 1 && numberOfHoldingDay <= 3) {
            return (int) (totalDeposit * 1.3);
        }
        if (numberOfHoldingDay > 3 && numberOfHoldingDay <= 7) {
            return (int) (totalDeposit * 1.7);
        }

        return (int) (totalDeposit * 1.0);
    }

    @Override
    @Transactional
    public void walletPaymentForBooking(Booking booking) {
        transactionUseCase.handlePaymentWallet(booking);

    }

    @Override
    @Transactional
    public void walletPaymentForMatchMaking(MatchMaking matchMaking) {
        transactionUseCase.handlePaymentWallet(matchMaking);
    }

    private List<PaymentRequest.ItemRequest> fromBooking(Booking booking) {
        List<PaymentRequest.ItemRequest> fromResources = booking.bookingSlots()
                .stream()
                .map(s -> {
                    String time = "%s-%s %s".formatted(
                            s.begin().format(DateTimeFormatter.ofPattern("HH:mm")),
                            s.end().format(DateTimeFormatter.ofPattern("HH:mm")),
                            booking.schedule().date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    );
                    String name = "%s (%s)".formatted(booking.stationResource().typeName(), time);
                    return PaymentRequest.ItemRequest.builder()
                            .name(name)
                            .price(booking.stationResource().price())
                            .quantity(1)
                            .unit("Resource")
                            .build();
                })
                .toList();
        List<PaymentRequest.ItemRequest> fromMenus = booking.bookingMenus()
                .stream()
                .map(m -> {
                    return PaymentRequest.ItemRequest.builder()
                            .name(m.menuName())
                            .price(m.price())
                            .quantity(1)
                            .unit("Menu")
                            .build();
                })
                .toList();

        return Stream.concat(fromResources.stream(), fromMenus.stream())
                .toList();
    }

    private List<PaymentRequest.ItemRequest> fromMatchMaking(MatchMaking matchMaking) {

        return matchMaking.resources()
                .stream()
                .map(s -> {
                    LocalTime begin = matchMaking.slots().stream()
                            .map(MatchMakingSlot::begin)
                            .min(Comparator.naturalOrder())
                            .orElseThrow(MyResourceNotFoundException::new);
                    LocalTime end = matchMaking.slots().stream()
                            .map(MatchMakingSlot::end)
                            .max(Comparator.naturalOrder())
                            .orElseThrow(MyResourceNotFoundException::new);
                    String beginStr = LocalDateTime.of(matchMaking.startAt(), begin)
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    String endStr = LocalDateTime.of(matchMaking.startAt(), end)
                            .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    String name = "%s (%s - %s)".formatted(s.typeName(), beginStr, endStr);
                    return PaymentRequest.ItemRequest.builder()
                            .name(name)
                            .price(s.price())
                            .quantity(1)
                            .unit("Resource")
                            .build();
                })
                .toList();
    }



    private PaymentResponse validResponse(PayOsResponse<PaymentResponse> response) {
        String receivedSignature = MyPaymentEncryptionUtils.encrypt(MyObjectMapper
                .convertFromObjectToString(response.data().generateRawSignature()));
        if (MyObjectUtils.isNotEquals(receivedSignature, response.signature())) {
            throw new MyAuthenticationException();
        }

        return response.data();
    }
}
