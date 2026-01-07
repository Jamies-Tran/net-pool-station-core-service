package net.pool.station.core.domain.transaction;

import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.match.making.MatchMaking;
import net.pool.station.core.domain.payment.PaymentWebhook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface TransactionUseCase {
    Transaction save(Transaction transaction);

    Page<Transaction> findAll(TransactionCriteria criteria, PageRequest pageRequest);

    Optional<Transaction> findByTransactionCode(DomainKey<String> transactionCode);

    void handlePaymentWallet(Booking booking);

    void handlePaymentWallet(MatchMaking matchMaking);

    void handlePaymentWebhook(DomainKey<String> transactionCode, PaymentWebhook paymentWebhook);
}
