package net.pool.station.core.features.timeslot.repository.database.dao;

import java.time.LocalDate;
import java.time.LocalTime;

public interface TimeSlotDao {
    Long getTimeSlotId();

    Long getScheduleId();

    LocalTime getBegin();

    LocalTime getEnd();

    String getPeriodCode();

    String getPeriodName();

    String getStatusCode();

    String getStatusName();

    LocalDate getDate();
}
