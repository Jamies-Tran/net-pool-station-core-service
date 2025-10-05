package net.pool.station.core.features.station.space.timeslot.controller.models;

import lombok.Builder;

@Builder
public record StationSpaceTimeSlotResponse(
        StationSpaceTimeSlotIdModel stationSpaceTimeSlotId,
        String statusCode,
        String statusName
) {
}
