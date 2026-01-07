package net.pool.station.core.domain.booking;

import lombok.With;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.booking.menu.BookingMenu;
import net.pool.station.core.domain.booking.slot.BookingSlot;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.domain.station.resource.StationResource;

import java.time.LocalDateTime;
import java.util.List;

public record Booking(
        Long bookingId,
        @With Long walletId,
        Long stationResourceId,
        Long scheduleId,
        Long matchMakingId,
        String bookingCode,
        String cancelReason,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName,
        LocalDateTime startAt,
        LocalDateTime endAt,
        @With Integer totalPrice,
        String paymentMethodCode,
        String paymentMethodName,
        @With Schedule schedule,
        @With StationResource stationResource,
        @With List<BookingMenu> bookingMenus,
        @With List<BookingSlot> bookingSlots,
        String createdBy
) {
    public Booking {
        if (MyObjectUtils.isNotEmpty(stationResource)
                && MyObjectUtils.isNotEmpty(stationResource.price())
                && MyObjectUtils.isNotEmpty(bookingMenus)
                && MyObjectUtils.isNotEmpty(bookingSlots)) {
            Integer resourcePrice = stationResource.price() * bookingSlots.size();
            Integer menuPrice = bookingMenus.stream()
                    .map(BookingMenu::price)
                    .reduce(0, Integer::sum);
            totalPrice = resourcePrice + menuPrice;
        }
    }
}
