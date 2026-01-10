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
import net.pool.station.core.features.booking.booking.repository.database.dao.TimeSlotDao;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingCommandService {
    BookingRepository repository;

    BookingEntityMapper mapper;

    protected Booking save(Booking booking) {
        LocalDate bookingDate = repository.findBookingDateByScheduleId(booking.scheduleId())
                .orElseThrow(() -> new MyResourceNotFoundException("Không tìm thấy lịch hoạt động"));
        List<Long> timeSlotIds = booking.bookingSlots().stream()
                .map(b -> b.bookingSlotId().timeSlotId())
                .toList();
        List<TimeSlotDao> timeSlotDao = repository.findTimeSlotByIdIn(timeSlotIds);
        LocalTime begin = timeSlotDao.stream()
                .map(TimeSlotDao::getBegin)
                .min(Comparator.naturalOrder())
                .orElseThrow(() -> new MyResourceNotFoundException("Không tìm thấy thời gian bắt đầu"));
        LocalTime end = timeSlotDao.stream()
                .map(TimeSlotDao::getEnd)
                .max(Comparator.naturalOrder())
                .orElseThrow(() -> new MyResourceNotFoundException("Không tìm thấy thời gian kết thúc"));
        BookingEntity saveBooking = mapper.toEntity(booking);
        saveBooking.setStartAt(LocalDateTime.of(bookingDate, begin));
        saveBooking.setEndAt(LocalDateTime.of(bookingDate, end));

        return mapper.toDto(repository.save(saveBooking));
    }

    protected Booking update(Long bookingId, Booking booking) {
        return repository.findByBookingIdAndDeletedFalse(bookingId)
                .map(
                        foundEntity -> {
                            validateChange(foundEntity);
                            LocalDate bookingDate = repository.findBookingDateByScheduleId(booking.scheduleId())
                                    .orElseThrow(() -> new MyResourceNotFoundException("Không tìm thấy lịch hoạt động"));
                            List<Long> timeSlotIds = booking.bookingSlots().stream()
                                    .map(b -> b.bookingSlotId().timeSlotId())
                                    .toList();
                            List<TimeSlotDao> timeSlotDao = repository.findTimeSlotByIdIn(timeSlotIds);
                            LocalTime begin = timeSlotDao.stream()
                                    .map(TimeSlotDao::getBegin)
                                    .min(Comparator.naturalOrder())
                                    .orElseThrow(() -> new MyResourceNotFoundException("Không tìm thấy thời gian bắt đầu"));
                            LocalTime end = timeSlotDao.stream()
                                    .map(TimeSlotDao::getEnd)
                                    .max(Comparator.naturalOrder())
                                    .orElseThrow(() -> new MyResourceNotFoundException("Không tìm thấy thời gian kết thúc"));
                            mapper.update(foundEntity, booking);
                            foundEntity.setStartAt(LocalDateTime.of(bookingDate, begin));
                            foundEntity.setEndAt(LocalDateTime.of(bookingDate, end));
                            return mapper.toDto(repository.save(foundEntity));
                        }
                ).orElseThrow(MyResourceNotFoundException::new);
    }

    protected Booking updateStatus(Long bookingId, EBookingStatus status) {
        return repository.findByBookingIdAndDeletedFalse(bookingId)
                .map(foundEntity -> {
                    foundEntity.setStatusCode(status.getCode());
                    foundEntity.setStatusName(status.getName());
                    return mapper.toDto(repository.save(foundEntity));
                })
                .orElseThrow(MyResourceNotFoundException::new);
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
            throw new MyResourceNotValid("Booking không thể thay đổi ngay lúc này");
        }
    }
}
