package net.pool.station.core.features.booking.booking.controller.models;

import java.util.List;

public record BookingRequest(
        Long scheduleId,
        Long stationResourceId,
        String typeCode,
        String typeName,
        String paymentMethodCode,
        String paymentMethodName,
        List<BookingMenuRequest> bookingMenus,
        List<BookingSlotRequest> bookingSlots
) {
    public record BookingMenuRequest(
            BookingMenuRequestId bookingMenuId
    ) {
        public record BookingMenuRequestId(
                Long stationMenuId
        ) {}
    }

    public record BookingSlotRequest(
            BookingSlotRequestId bookingSlotId
    ) {
        public record BookingSlotRequestId(
                Long timeSlotId
        ) {}
    }
}
