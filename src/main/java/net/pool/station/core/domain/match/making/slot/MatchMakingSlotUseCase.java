package net.pool.station.core.domain.match.making.slot;

import net.pool.station.core.domain.DomainKey;

import java.util.List;

public interface MatchMakingSlotUseCase {
    void save(DomainKey<Long> matchMakingId, List<MatchMakingSlot> matchMakingSlots);

    void update(DomainKey<Long> matchMakingId, List<MatchMakingSlot> matchMakingSlots);

    List<MatchMakingSlot> findAllByMatchMakingId(DomainKey<Long> matchMakingId);

    List<MatchMakingSlot> findAllByStationResourceIdAndTimeSlotIdIn(DomainKey<Long> stationResourceId, List<Long> timeSlotIds);
}
