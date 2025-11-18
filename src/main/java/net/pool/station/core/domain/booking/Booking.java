package net.pool.station.core.domain.booking;

import lombok.With;
import net.pool.station.core.domain.booking.resource.BookingResource;
import net.pool.station.core.domain.schedule.Schedule;

import java.util.List;

public record Booking(
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
        @With Schedule schedule,
        @With List<BookingResource> bookingResources
) {
}
