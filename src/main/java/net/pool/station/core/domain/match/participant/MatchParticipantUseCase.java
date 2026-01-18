package net.pool.station.core.domain.match.participant;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MatchParticipantUseCase {
    void save(DomainKey<Long> matchMakingId, List<MatchParticipant> matchParticipants);

    void fillEmptyParticipant(DomainKey<Long> matchMakingId, Long accountId);

    MatchParticipantCancel emptyFilledParticipant(DomainKey<Long> matchParticipantId);

    List<MatchParticipant> findAllByMatchMakingId(DomainKey<Long> matchMakingId);

    Page<MatchParticipant> findAll(MatchParticipantCriteria criteria, PageRequest pageRequest);

    Optional<MatchParticipant> findById(DomainKey<Long> matchParticipantId);

    void ready(DomainKey<Long> matchParticipantId, LocalDateTime paidShareAt);
}
