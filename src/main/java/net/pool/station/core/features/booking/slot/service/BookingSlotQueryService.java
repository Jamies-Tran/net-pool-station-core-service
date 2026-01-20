package net.pool.station.core.features.booking.slot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.booking.slot.BookingSlot;
import net.pool.station.core.features.booking.slot.repository.database.BookingSlotEntityMapper;
import net.pool.station.core.features.booking.slot.repository.database.BookingSlotRepository;
import net.pool.station.core.features.booking.slot.repository.database.dao.BookingSlotDaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingSlotQueryService {
    BookingSlotRepository repository;

    BookingSlotDaoMapper daoMapper;

    BookingSlotEntityMapper mapper;

    protected List<BookingSlot> findAllByBookingId(Long bookingId) {
        return daoMapper.toDto(repository.findAllByBookingId(bookingId));
    }

    protected List<BookingSlot> findAllByStationResourceIdAndTimeSlotIdInAndBookingStatusCodeInd(
            Long stationResourceId,
            List<Long> timeSlotIds,
            List<String> bookingStatusCodes
    ) {
        return daoMapper.toDto(repository
                .findAllByStationResourceIdAndTimeSlotIdInAndBookingStatusCodeIn(stationResourceId, timeSlotIds,
                        bookingStatusCodes));
    }
}
