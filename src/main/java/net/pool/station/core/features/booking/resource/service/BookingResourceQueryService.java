package net.pool.station.core.features.booking.resource.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.booking.resource.BookingResource;
import net.pool.station.core.features.booking.resource.repository.database.BookingResourceEntityMapper;
import net.pool.station.core.features.booking.resource.repository.database.BookingResourceRepository;
import net.pool.station.core.features.booking.resource.repository.database.dao.BookingResourceDaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingResourceQueryService {
    BookingResourceRepository repository;

    BookingResourceEntityMapper mapper;

    BookingResourceDaoMapper daoMapper;

    protected List<BookingResource> findAllByBookingId(Long bookingId) {
        return daoMapper.toDto(repository.findAllByBookingId(bookingId));
    }
}
