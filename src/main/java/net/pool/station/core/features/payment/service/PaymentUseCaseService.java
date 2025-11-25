package net.pool.station.core.features.payment.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
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

import java.time.format.DateTimeFormatter;
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
        Account account = accountUseCase.findById(new DomainKey<>(booking.accountId()))
                .orElseThrow(MyResourceNotFoundException::new);
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

    private List<PaymentRequest.ItemRequest> fromBooking(Booking booking) {
        List<PaymentRequest.ItemRequest> fromResources = booking.bookingSlots()
                .stream()
                .map(s -> {
                    String time = "%s-%s %s".formatted(
                            s.begin().format(DateTimeFormatter.ofPattern("hh:mm")),
                            s.end().format(DateTimeFormatter.ofPattern("hh:mm")),
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


    private PaymentResponse validResponse(PayOsResponse<PaymentResponse> response) {
        String receivedSignature = MyPaymentEncryptionUtils.encrypt(MyObjectMapper
                .convertFromObjectToString(response.data().generateRawSignature()));
        if (MyObjectUtils.isNotEquals(receivedSignature, response.signature())) {
            throw new MyAuthenticationException();
        }

        return response.data();
    }
}
