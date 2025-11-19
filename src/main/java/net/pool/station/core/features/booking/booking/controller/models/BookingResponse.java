package net.pool.station.core.features.booking.booking.controller.models;

import lombok.With;
import net.pool.station.core.domain.booking.menu.BookingMenu;
import net.pool.station.core.domain.booking.menu.BookingMenuId;
import net.pool.station.core.domain.booking.resource.BookingResource;
import net.pool.station.core.domain.booking.resource.BookingResourceId;
import net.pool.station.core.domain.booking.slot.BookingSlot;
import net.pool.station.core.domain.booking.slot.BookingSlotId;
import net.pool.station.core.domain.schedule.Schedule;

import java.time.LocalTime;
import java.util.List;

public record BookingResponse(
        Long bookingId,
        Long accountId,
        Long scheduleId,
        Long matchMakingId,
        String bookingCode,
        String cancelReason,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName,
        Schedule schedule,
        List<BookingResourceResponse> bookingResources,
        List<BookingMenuResponse> bookingMenus,
        List<BookingSlotResponse> bookingSlots

) {
    public record BookingResourceResponse(
            BookingResourceId bookingResourceId,
            String resourceCode,
            String resourceName,
            String typeCode,
            String typeName,
            Integer price
    ) {}

    public record BookingMenuResponse(
            BookingMenuId bookingMenuId,
            String menuCode,
            String menuName,
            String typeCode,
            String typeName,
            Integer price
    ) {}

    public record BookingSlotResponse(
            BookingSlotId bookingSlotId,
            LocalTime begin,
            LocalTime end,
            String periodCode,
            String periodName
    ) {}
}
