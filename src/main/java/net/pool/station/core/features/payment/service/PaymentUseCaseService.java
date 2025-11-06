package net.pool.station.core.features.payment.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EPaymentType;
import net.pool.station.core.bootstrap.rest.response.PayOsResponse;
import net.pool.station.core.bootstrap.utils.MyHMacEncryptionUtils;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.account.Account;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.payment.Payment;
import net.pool.station.core.domain.payment.PaymentUseCase;
import net.pool.station.core.domain.transaction.Transaction;
import net.pool.station.core.domain.transaction.TransactionUseCase;
import net.pool.station.core.features.payment.repository.feign.PaymentPlaceHolder;
import net.pool.station.core.features.payment.repository.feign.models.PaymentRequest;
import net.pool.station.core.features.payment.repository.feign.models.PaymentResponse;
import net.pool.station.core.features.payment.repository.feign.models.PaymentResponseMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentUseCaseService implements PaymentUseCase {
    PaymentPlaceHolder paymentPlaceHolder;

    TransactionUseCase transactionUseCase;

    AccountUseCase accountUseCase;

    PaymentResponseMapper mapper;

    @NonFinal
    @Value("${environment.payOs.cancelUrl}")
    String cancelUrl;

    @NonFinal
    @Value("${environment.payOs.returnUrl}")
    String returnUrl;

    @Override
    public Payment create(Payment payment) {
        LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                .orElseThrow(MyAuthenticationException::new);
        Account account = accountUseCase.findByEmail(DomainKey.of(loginInfo.email()))
                .orElseThrow(MyResourceNotFoundException::new);
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderCode(account.accountId().toString())
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
                                .build()
                ))
                .cancelUrl(cancelUrl)
                .returnUrl(returnUrl)
                .build();
        PaymentResponse paymentResponse = validResponse(paymentPlaceHolder.requestPayment(paymentRequest));
        Transaction transaction = Transaction.builder()
                .walletId(payment.walletId())
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

    private PaymentResponse validResponse(PayOsResponse<PaymentResponse> response) {
        String receivedSignature = MyHMacEncryptionUtils.encrypt(response.data());
        if (MyObjectUtils.isNotEquals(receivedSignature, response.signature())) {
            throw new MyAuthenticationException();
        }

        return response.data();
    }
}
