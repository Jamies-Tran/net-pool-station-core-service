package net.pool.station.core.domain.booking.menu;

import lombok.Builder;
import lombok.With;

@Builder
public record BookingMenuId(
        @With Long bookingId,
        Long stationMenuId
) {
}
