package net.pool.station.core.features.timeslot.repository.database.dao;

import net.pool.station.core.features.timeslot.repository.database.TimeSlotEntity;

public interface TimeSlotAllowBookingDao {
    TimeSlotEntity getTimeSlot();

    Long getTimeSlotId();

    Boolean getAllowBooking();
}
