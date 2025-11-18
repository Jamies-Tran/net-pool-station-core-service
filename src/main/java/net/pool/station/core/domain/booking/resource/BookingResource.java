package net.pool.station.core.domain.booking.resource;

import lombok.With;

public record BookingResource(
    @With BookingResourceId id
) {
}
