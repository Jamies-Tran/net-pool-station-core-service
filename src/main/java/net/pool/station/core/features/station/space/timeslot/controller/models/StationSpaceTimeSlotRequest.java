package net.pool.station.core.features.station.space.timeslot.controller.models;

public record StationSpaceTimeSlotRequest(
        Long stationSpaceId,
        Long timeSlotId
) {
}
