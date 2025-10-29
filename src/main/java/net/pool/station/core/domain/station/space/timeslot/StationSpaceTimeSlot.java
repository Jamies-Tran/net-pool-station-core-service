package net.pool.station.core.domain.station.space.timeslot;

public record StationSpaceTimeSlot(
        Long stationSpaceSlotId,
        Long stationSpaceId,
        Long timeSlotId,
        String statusCode,
        String statusName
) {
}
