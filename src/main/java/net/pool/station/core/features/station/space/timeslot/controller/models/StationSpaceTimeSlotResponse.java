package net.pool.station.core.features.station.space.timeslot.controller.models;

import lombok.Builder;

@Builder
public record StationSpaceTimeSlotResponse(
        Long stationSpaceSlotId,
        Long stationSpaceId,
        Long timeSlotId,
        String statusCode,
        String statusName
) {
}
