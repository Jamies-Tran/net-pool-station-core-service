package net.pool.station.core.domain.booking.slot;

import lombok.With;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingSlot(
        @With BookingSlotId bookingSlotId,
        LocalDate date,
        LocalTime begin,
        LocalTime end,
        String periodCode,
        String periodName
) {
}
