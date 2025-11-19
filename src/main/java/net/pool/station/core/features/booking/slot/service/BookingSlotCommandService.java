package net.pool.station.core.features.booking.slot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.booking.slot.BookingSlot;
import net.pool.station.core.domain.booking.slot.BookingSlotId;
import net.pool.station.core.features.booking.slot.repository.database.BookingSlotEntityMapper;
import net.pool.station.core.features.booking.slot.repository.database.BookingSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingSlotCommandService {
    BookingSlotRepository repository;

    BookingSlotEntityMapper mapper;

    protected void saveAll(Long bookingId, List<BookingSlot> bookingSlots) {
        bookingSlots = bookingSlots.stream()
                .map(bookingSlot -> {
                    BookingSlotId bookingSlotId = bookingSlot.bookingSlotId().withBookingId(bookingId);

                    return bookingSlot.withBookingSlotId(bookingSlotId);
                })
                .toList();
        repository.saveAll(mapper.toEntity(bookingSlots));
    }
}
