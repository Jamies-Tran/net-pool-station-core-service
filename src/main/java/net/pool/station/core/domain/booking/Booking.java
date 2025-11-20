package net.pool.station.core.domain.booking;

import lombok.With;
import net.pool.station.core.domain.booking.menu.BookingMenu;
import net.pool.station.core.domain.booking.resource.BookingResource;
import net.pool.station.core.domain.booking.slot.BookingSlot;
import net.pool.station.core.domain.schedule.Schedule;

import java.util.List;

public record Booking(
        Long bookingId,
        @With Long accountId,
        Long scheduleId,
        Long matchMakingId,
        String bookingCode,
        String cancelReason,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName,
        @With Schedule schedule,
        @With List<BookingResource> bookingResources,
        @With List<BookingMenu> bookingMenus,
        @With List<BookingSlot> bookingSlots
) {
}
