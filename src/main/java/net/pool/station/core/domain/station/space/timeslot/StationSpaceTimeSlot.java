package net.pool.station.core.domain.station.space.timeslot;

public record StationSpaceTimeSlot(
    StationSpaceTimeSlotId stationSpaceTimeSlotId,
    String statusCode,
    String statusName
) {
}
