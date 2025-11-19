package net.pool.station.core.domain.booking.menu;

import lombok.With;

public record BookingMenu(
        @With BookingMenuId bookingMenuId,
        String menuCode,
        String menuName,
        String typeCode,
        String typeName,
        Integer price
) {
}
