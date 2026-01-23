package net.pool.station.core.domain.match.schedule;

import net.pool.station.core.domain.DomainKey;

import java.util.List;

public interface MatchScheduleUseCase {
    void save(DomainKey<Long> matchMakingSlotId, List<MatchSchedule> matchSchedules);

    void update(DomainKey<Long> matchMakingSlotId, List<MatchSchedule> matchSchedules);

    List<MatchSchedule> findAllByMatchMakingId(DomainKey<Long> matchMakingId);
}
