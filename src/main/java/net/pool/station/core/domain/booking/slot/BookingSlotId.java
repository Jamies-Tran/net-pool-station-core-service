package net.pool.station.core.domain.booking.slot;

import lombok.With;

public record BookingSlotId(
        @With Long bookingId,
        Long timeSlotId
) {
}
