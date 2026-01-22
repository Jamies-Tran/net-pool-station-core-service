package net.pool.station.core.features.booking.slot.repository.database.dao;

import net.pool.station.core.domain.booking.slot.BookingSlotId;

import java.time.LocalDate;
import java.time.LocalTime;

public interface BookingSlotDao {
    BookingSlotDaoId getBookingSlotId();

    LocalDate getDate();

    LocalTime getBegin();

    LocalTime getEnd();

    String getPeriodCode();

    String getPeriodName();
}
