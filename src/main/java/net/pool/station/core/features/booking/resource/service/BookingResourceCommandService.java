package net.pool.station.core.features.booking.resource.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.booking.resource.BookingResource;
import net.pool.station.core.domain.booking.resource.BookingResourceId;
import net.pool.station.core.features.booking.resource.repository.database.BookingResourceEntityMapper;
import net.pool.station.core.features.booking.resource.repository.database.BookingResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingResourceCommandService {
    BookingResourceRepository repository;

    BookingResourceEntityMapper mapper;

    protected void save(Long bookingId, List<BookingResource> bookingResources) {
        bookingResources = bookingResources.stream()
                .map(bookingResource -> {
                    BookingResourceId bookingResourceId = bookingResource.bookingResourceId().withBookingId(bookingId);
                    return bookingResource.withBookingResourceId(bookingResourceId);
                })
                .toList();

        repository.saveAll(mapper.toEntity(bookingResources));
    }
}
