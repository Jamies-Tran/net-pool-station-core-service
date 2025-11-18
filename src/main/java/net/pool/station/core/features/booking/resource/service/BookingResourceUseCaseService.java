package net.pool.station.core.features.booking.resource.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.resource.BookingResource;
import net.pool.station.core.domain.booking.resource.BookingResourceUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingResourceUseCaseService implements BookingResourceUseCase {
    BookingResourceCommandService commandService;

    BookingResourceQueryService queryService;

    @Override
    @Transactional
    public void save(DomainKey<Long> bookingId, List<BookingResource> bookingResources) {
        commandService.save(bookingId.value(), bookingResources);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingResource> findAllByBookingId(DomainKey<Long> bookingId) {
        return queryService.findAllByBookingId(bookingId.value());
    }
}
