package net.pool.station.core.domain.station.space.schedule;

import net.pool.station.core.domain.DomainKey;

import java.util.List;

public interface StationSpaceScheduleUseCase {
    void save(DomainKey<Long> scheduleId, List<StationSpaceSchedule> stationSpaceSchedules);
}
