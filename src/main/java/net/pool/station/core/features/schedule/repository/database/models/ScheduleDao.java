package net.pool.station.core.features.schedule.repository.database.models;

import java.time.LocalDate;

public interface ScheduleDao {
    Long getScheduleId();

    Long getStationId();

    LocalDate getDate();

    String getStatusCode();

    String getStatusName();
}
