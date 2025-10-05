package net.pool.station.core.features.station.space.timeslot.controller.models;

import java.util.List;

public record StationSpaceTimeSlotListRequest(
        List<StationSpaceTimeSlotRequest> stationSpaceTimeSlots
) {
}
