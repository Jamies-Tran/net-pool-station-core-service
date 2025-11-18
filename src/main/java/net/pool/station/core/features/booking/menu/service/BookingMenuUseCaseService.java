package net.pool.station.core.features.booking.menu.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.menu.BookingMenu;
import net.pool.station.core.domain.booking.menu.BookingMenuUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingMenuUseCaseService implements BookingMenuUseCase {
    @Override
    @Transactional
    public void save(DomainKey<Long> bookingId, List<BookingMenu> bookingMenus) {

    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingMenu> findAllByBookingId(DomainKey<Long> bookingId) {
        return List.of();
    }
}
