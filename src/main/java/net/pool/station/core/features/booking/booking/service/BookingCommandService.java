package net.pool.station.core.features.booking.booking.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EBookingStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.features.booking.booking.repository.database.BookingEntity;
import net.pool.station.core.features.booking.booking.repository.database.BookingEntityMapper;
import net.pool.station.core.features.booking.booking.repository.database.BookingRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingCommandService {
    BookingRepository repository;

    BookingEntityMapper mapper;

    protected Booking save(Booking booking) {
        return mapper.toDto(repository.save(mapper.toEntity(booking)));
    }

    protected void update(Long bookingId, Booking booking) {
        repository.findByBookingIdAndDeletedFalse(bookingId)
                .ifPresentOrElse(
                        foundEntity -> {
                            validateChange(foundEntity);
                            mapper.update(foundEntity, booking);
                            repository.save(foundEntity);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void updateStatus(Long bookingId, EBookingStatus status) {
        repository.findByBookingIdAndDeletedFalse(bookingId)
                .ifPresentOrElse(
                        foundEntity -> {
                            foundEntity.setStatusCode(status.getCode());
                            foundEntity.setStatusName(status.getName());
                            repository.save(foundEntity);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void updateStatus(Long bookingId, String cancelReason, EBookingStatus status) {
        repository.findByBookingIdAndDeletedFalse(bookingId)
                .ifPresentOrElse(
                        foundEntity -> {
                            foundEntity.setStatusCode(status.getCode());
                            foundEntity.setStatusName(status.getName());
                            foundEntity.setCancelReason(cancelReason);
                            repository.save(foundEntity);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void delete(Long bookingId) {
        repository.findByBookingIdAndDeletedFalse(bookingId)
                .ifPresentOrElse(
                        foundEntity -> {
                            validateChange(foundEntity);
                            foundEntity.setDeleted(true);
                            repository.save(foundEntity);
                        },
                        () -> {}
                );
    }

    private void validateChange(BookingEntity booking) {
        if (MyObjectUtils.isNotEquals(booking.getStatusCode(), EBookingStatus.NEW.getCode())) {
            throw new MyResourceNotValid();
        }
    }
}
