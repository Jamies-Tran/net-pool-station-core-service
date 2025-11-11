package net.pool.station.core.domain.station.space.schedule.slot;

import lombok.With;

public record StationSpaceScheduleSlot(
        Long stationSpaceScheduleSlotId,
        Long timeSlotId,
        @With Long stationSpaceScheduleId
) {
}
