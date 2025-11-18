package net.pool.station.core.features.booking.menu.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.booking.menu.BookingMenu;
import net.pool.station.core.domain.booking.menu.BookingMenuId;
import net.pool.station.core.features.booking.menu.repository.database.BookingMenuEntityMapper;
import net.pool.station.core.features.booking.menu.repository.database.BookingMenuRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingMenuCommandService {
    BookingMenuRepository repository;

    BookingMenuEntityMapper mapper;

    protected void saveAll(Long bookingId, List<BookingMenu> bookingMenus) {
        bookingMenus = bookingMenus.stream()
                .map(bookingMenu -> {
                    BookingMenuId bookingMenuId = bookingMenu.id().withBookingId(bookingId);
                    return bookingMenu.withId(bookingMenuId);
                })
                .toList();
        repository.saveAll(mapper.toEntity(bookingMenus));
    }
}
