package net.pool.station.core.features.payment.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.payment.Payment;
import net.pool.station.core.domain.payment.PaymentUseCase;
import net.pool.station.core.domain.transaction.TransactionUseCase;
import net.pool.station.core.features.payment.repository.feign.PaymentPlaceHolder;
import net.pool.station.core.features.payment.repository.feign.models.PaymentRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentUseCaseService implements PaymentUseCase {
    PaymentPlaceHolder paymentPlaceHolder;

    TransactionUseCase transactionUseCase;

    AccountUseCase accountUseCase;

    @Override
    public Payment create(Payment payment) {


        return null;
    }
}
