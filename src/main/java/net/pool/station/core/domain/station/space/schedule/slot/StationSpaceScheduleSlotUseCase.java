package net.pool.station.core.domain.station.space.schedule.slot;

import net.pool.station.core.domain.DomainKey;

import java.util.List;

public interface StationSpaceScheduleSlotUseCase {
    void save(DomainKey<Long> stationSpaceScheduleId, List<StationSpaceScheduleSlot> slots);
}
