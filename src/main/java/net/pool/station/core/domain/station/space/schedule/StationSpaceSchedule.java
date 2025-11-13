package net.pool.station.core.domain.station.space.schedule;

import lombok.With;

public record StationSpaceSchedule(
        Long stationSpaceScheduleId,
        Long scheduleId,
        @With Long stationSpaceId,
        String statusCode,
        String statusName
) {
}
