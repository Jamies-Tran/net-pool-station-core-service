package net.pool.station.core.domain.booking;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface BookingUseCase {
    void save(Booking booking);

    Optional<Booking> findById(DomainKey<Long> bookingId);

    Page<Booking> findAll(BookingCriteria criteria, PageRequest pageRequest);

    void update(DomainKey<Long> bookingId, Booking booking);

    void start(DomainKey<Long> bookingId);

    void finish(DomainKey<Long> bookingId);

    void cancel(DomainKey<Long> bookingId, String cancelReason);

    void delete(DomainKey<Long> bookingId);
}
