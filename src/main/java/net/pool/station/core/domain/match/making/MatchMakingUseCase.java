package net.pool.station.core.domain.match.making;

import net.pool.station.core.bootstrap.enums.EPaymentMethod;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.payment.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Optional;

public interface MatchMakingUseCase {
    Long save(MatchMaking matchMaking);

    void update(DomainKey<Long> matchMakingId, MatchMaking matchMaking);

    void process(DomainKey<Long> matchMakingId, Integer paidDeposit, LocalDateTime paidDepositAt);

    void processParticipant(DomainKey<Long> matchParticipantId, LocalDateTime paidShareAt);

    void prepareToStart(DomainKey<Long> matchMakingId);

    void start(DomainKey<Long> matchMakingId);

    void cancel(DomainKey<Long> matchMakingId);

    void finish(DomainKey<Long> matchMakingId);

    void handleExpiredJob(DomainKey<Long> matchMakingId);

    void delete(DomainKey<Long> matchMakingId);

    Optional<MatchMaking> findById(DomainKey<Long> matchMakingId);

    Page<MatchMaking> findAll(MatchMakingCriteria criteria, PageRequest pageRequest);

    Optional<Payment> generateDepositPayment(DomainKey<Long> matchMakingId);

    void depositWalletPayment(DomainKey<Long> matchMakingId);

    void emptyParticipant(DomainKey<Long> matchParticipantId);

    void participantWalletPayment(DomainKey<Long> matchParticipantId, EPaymentMethod paymentMethod);

    void prepareStart(DomainKey<Long> matchMakingId);
}
