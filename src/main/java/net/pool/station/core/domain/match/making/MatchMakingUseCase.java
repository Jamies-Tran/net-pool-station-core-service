package net.pool.station.core.domain.match.making;

import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.payment.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface MatchMakingUseCase {
    Long save(MatchMaking matchMaking);

    void update(DomainKey<Long> matchMakingId, MatchMaking matchMaking);

    void process(DomainKey<Long> matchMakingId);

    void start(DomainKey<Long> matchMakingId);

    void cancel(DomainKey<Long> matchMakingId);

    void finish(DomainKey<Long> matchMakingId);

    void handleExpiredJob(DomainKey<Long> matchMakingId);

    void delete(DomainKey<Long> matchMakingId);

    Optional<MatchMaking> findById(DomainKey<Long> matchMakingId);

    Page<MatchMaking> findAll(MatchMakingCriteria criteria, PageRequest pageRequest);

    Optional<Payment> generatePayment(DomainKey<Long> matchMakingId);

    void walletPayment(DomainKey<Long> matchMakingId);
}
