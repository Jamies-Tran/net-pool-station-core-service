package net.pool.station.core.features.booking.booking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EBookingStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.booking.BookingCriteria;
import net.pool.station.core.domain.booking.BookingUseCase;
import net.pool.station.core.domain.booking.resource.BookingResource;
import net.pool.station.core.domain.booking.resource.BookingResourceUseCase;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.domain.schedule.ScheduleUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingUseCaseService implements BookingUseCase {
    BookingCommandService commandService;

    BookingQueryService queryService;

    BookingResourceUseCase bookingResourceUseCase;

    ScheduleUseCase scheduleUseCase;

    @Override
    @Transactional
    public void save(Booking booking) {

        Booking savedBooking = commandService.save(booking);

        bookingResourceUseCase.save(DomainKey.of(savedBooking.bookingId()), booking.bookingResources());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Booking> findById(DomainKey<Long> bookingId) {
        return queryService.findById(bookingId.value())
                .map(booking -> {
                    Schedule schedule = scheduleUseCase.findById(DomainKey.of(booking.scheduleId()))
                            .orElse(null);
                    List<BookingResource> bookingResources = bookingResourceUseCase
                            .findAllByBookingId(DomainKey.of(booking.bookingId()));
                    return booking.withSchedule(schedule).withBookingResources(bookingResources);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Booking> findAll(BookingCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> bookingId, Booking booking) {
        commandService.update(bookingId.value(), booking);
    }

    @Override
    @Transactional
    public void start(DomainKey<Long> bookingId) {
        commandService.updateStatus(bookingId.value(), EBookingStatus.PROCESSING);
    }

    @Override
    @Transactional
    public void finish(DomainKey<Long> bookingId) {
        commandService.updateStatus(bookingId.value(), EBookingStatus.COMPLETED);
    }

    @Override
    @Transactional
    public void cancel(DomainKey<Long> bookingId, String cancelReason) {
        commandService.updateStatus(bookingId.value(), cancelReason, EBookingStatus.CANCELED);
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> bookingId) {
        commandService.delete(bookingId.value());
    }
}
