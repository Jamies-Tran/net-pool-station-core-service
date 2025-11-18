package net.pool.station.core.features.booking.booking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.booking.BookingCriteria;
import net.pool.station.core.features.booking.booking.repository.database.BookingEntityMapper;
import net.pool.station.core.features.booking.booking.repository.database.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingQueryService {
    BookingRepository repository;

    BookingEntityMapper mapper;

    protected Optional<Booking> findById(Long bookingId) {
        return repository.findByBookingIdAndDeletedFalse(bookingId)
                .map(mapper::toDto);
    }

    protected Page<Booking> findAll(BookingCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }
}
