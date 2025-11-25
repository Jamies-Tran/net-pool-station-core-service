package net.pool.station.core.features.booking.booking.repository.database.dao;

import java.time.LocalTime;

public interface TimeSlotDao {
    LocalTime getBegin();

    LocalTime getEnd();
}
