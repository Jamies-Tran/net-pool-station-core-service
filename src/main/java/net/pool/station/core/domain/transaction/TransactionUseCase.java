package net.pool.station.core.domain.transaction;

import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.match.making.MatchMaking;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import net.pool.station.core.domain.payment.PaymentWebhook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface TransactionUseCase {
    Transaction save(Transaction transaction);

    Page<Transaction> findAll(TransactionCriteria criteria, PageRequest pageRequest);

    List<Transaction> findAllByIdIn(List<Long> transactionIds);

    void handlePaymentWallet(Booking booking);

    void handleDepositPaymentWallet(MatchMaking matchMaking);

    void handlePaymentWallet(MatchParticipant matchParticipant);

    void handlePaymentWebhook(DomainKey<String> transactionCode, PaymentWebhook paymentWebhook);

    void handleRefundMatchMaking(MatchMaking matchMaking);

    void handleRefundMatchParticipant(MatchParticipant matchParticipant);

    void handleStartMatchMaking(MatchMaking matchMaking);
}
