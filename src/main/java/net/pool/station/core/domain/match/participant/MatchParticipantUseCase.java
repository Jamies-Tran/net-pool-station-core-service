package net.pool.station.core.domain.match.participant;

import net.pool.station.core.domain.DomainKey;

import java.util.List;

public interface MatchParticipantUseCase {
    void save(DomainKey<Long> matchMakingId, List<MatchParticipant> matchParticipants);
}
