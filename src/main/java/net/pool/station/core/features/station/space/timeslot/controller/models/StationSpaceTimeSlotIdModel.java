package net.pool.station.core.features.station.space.timeslot.controller.models;

public record StationSpaceTimeSlotIdModel(
        Long stationId,
        Long spaceId,
        Long timeSlotId
) {
}
