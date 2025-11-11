package net.pool.station.core.features.station.space.schedule.slot.controller.models;

import java.util.List;

public record StationSpaceScheduleSlotListRequest(
        List<StationSpaceScheduleSlotRequest> stationSpaceScheduleSlots
) {
    public record StationSpaceScheduleSlotRequest(
            Long timeSlotId
    ) {}
}
