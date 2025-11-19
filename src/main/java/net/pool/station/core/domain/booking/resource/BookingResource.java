package net.pool.station.core.domain.booking.resource;

import lombok.With;

public record BookingResource(
    @With BookingResourceId bookingResourceId,
    String resourceCode,
    String resourceName,
    String typeCode,
    String typeName,
    Integer price
) {
}
