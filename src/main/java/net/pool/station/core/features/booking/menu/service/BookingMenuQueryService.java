package net.pool.station.core.features.booking.menu.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.booking.menu.BookingMenu;
import net.pool.station.core.features.booking.menu.repository.database.BookingMenuEntityMapper;
import net.pool.station.core.features.booking.menu.repository.database.BookingMenuRepository;
import net.pool.station.core.features.booking.menu.repository.database.dao.BookingMenuDao;
import net.pool.station.core.features.booking.menu.repository.database.dao.BookingMenuDaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingMenuQueryService {
    BookingMenuRepository repository;

    BookingMenuDaoMapper mapper;

    protected List<BookingMenu> findAllByBookingId(Long bookingId) {
        return mapper.toDto(repository.findAllByBookingId(bookingId));
    }
}
