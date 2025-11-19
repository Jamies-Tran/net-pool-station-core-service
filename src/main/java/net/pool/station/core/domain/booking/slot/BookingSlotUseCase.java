package net.pool.station.core.domain.booking.slot;

import net.pool.station.core.domain.DomainKey;

import java.util.List;

public interface BookingSlotUseCase {
    void save(DomainKey<Long> bookingId, List<BookingSlot> bookingSlots);

    List<BookingSlot> findAllByBookingId(DomainKey<Long> bookingId);
}
