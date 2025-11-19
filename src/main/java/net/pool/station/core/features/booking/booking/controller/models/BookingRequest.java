package net.pool.station.core.features.booking.booking.controller.models;

import java.util.List;

public record BookingRequest(
        Long scheduleId,
        String typeCode,
        String typeName,
        List<BookingResourceRequest> bookingResources,
        List<BookingMenuRequest> bookingMenus,
        List<BookingSlotRequest> bookingSlots
) {
    public record BookingResourceRequest(
            BookingResourceRequestId bookingResourceId
    ) {
        public record BookingResourceRequestId(
                Long stationResourceId
        ) {}
    }

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
