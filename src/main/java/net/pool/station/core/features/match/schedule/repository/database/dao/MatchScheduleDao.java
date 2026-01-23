package net.pool.station.core.features.match.schedule.repository.database.dao;

import net.pool.station.core.features.match.schedule.repository.database.MatchScheduleEntityId;

import java.time.LocalDate;

public interface MatchScheduleDao {
    MatchScheduleEntityId getId();

    LocalDate getDate();
}
