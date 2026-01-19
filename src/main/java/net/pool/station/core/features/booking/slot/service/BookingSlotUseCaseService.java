package net.pool.station.core.features.booking.slot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.slot.BookingSlot;
import net.pool.station.core.domain.booking.slot.BookingSlotUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingSlotUseCaseService implements BookingSlotUseCase {
    BookingSlotCommandService commandService;

    BookingSlotQueryService queryService;

    @Override
    @Transactional
    public void save(DomainKey<Long> bookingId, List<BookingSlot> bookingSlots) {
        commandService.saveAll(bookingId.value(), bookingSlots);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> bookingId, List<BookingSlot> bookingSlots) {
        commandService.update(bookingId.value(), bookingSlots);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingSlot> findAllByBookingId(DomainKey<Long> bookingId) {
        return queryService.findAllByBookingId(bookingId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingSlot> findAllByStationResourceIdAndTimeSlotIdIn(DomainKey<Long> stationResourceId, List<Long> timeSlotIds) {
        return queryService.findAllByStationResourceIdAndTimeSlotIdIn(stationResourceId.value(), timeSlotIds);
    }
}
