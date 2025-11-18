package net.pool.station.core.features.booking.booking.controller.models;

public record BookingRequest(
        Long scheduleId,
        String typeCode,
        String typeName
) {
}
