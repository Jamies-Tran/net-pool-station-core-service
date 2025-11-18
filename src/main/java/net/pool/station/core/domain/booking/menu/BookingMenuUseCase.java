package net.pool.station.core.domain.booking.menu;

import net.pool.station.core.domain.DomainKey;

import java.util.List;

public interface BookingMenuUseCase {
    void save(DomainKey<Long> bookingId, List<BookingMenu> bookingMenus);

    List<BookingMenu> findAllByBookingId(DomainKey<Long> bookingId);
}
