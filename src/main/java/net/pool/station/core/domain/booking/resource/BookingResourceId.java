package net.pool.station.core.domain.booking.resource;


import lombok.With;

public record BookingResourceId(
        @With Long bookingId,
        Long stationResourceId
) {
}
