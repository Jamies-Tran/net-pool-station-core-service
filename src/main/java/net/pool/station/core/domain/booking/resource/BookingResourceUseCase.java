package net.pool.station.core.domain.booking.resource;

import net.pool.station.core.domain.DomainKey;

import java.util.List;

public interface BookingResourceUseCase {
    void save(DomainKey<Long> bookingId, List<BookingResource> bookingResources);

    List<BookingResource> findAllByBookingId(DomainKey<Long> bookingId);
}
