package net.pool.station.core.features.booking.booking.controller.models;

import net.pool.station.core.domain.booking.menu.BookingMenuId;
import net.pool.station.core.domain.booking.slot.BookingSlotId;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.features.account.account.controller.models.AccountResponse;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public record BookingResponse(
        Long bookingId,
        Long scheduleId,
        Long stationResourceId,
        Long matchMakingId,
        Integer totalPrice,
        String bookingCode,
        String cancelReason,
        String typeCode,
        String typeName,
        LocalDateTime startAt,
        LocalDateTime endAt,
        String paymentMethodCode,
        String paymentMethodName,
        String statusCode,
        String statusName,
        String createdBy,
        Schedule schedule,
        StationResourceResponse stationResource,
        List<BookingMenuResponse> bookingMenus,
        List<BookingSlotResponse> bookingSlots,
        AccountResponse account

) {
    public record StationResourceResponse(
            Long stationResourceId,
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
