package net.pool.station.core.features.match.making.slot.respository.database.dao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public interface MatchMakingSlotDao {
    MatchMakingSlotDaoId getId();

    Long getTimeSlotId();

    LocalTime getBegin();

    LocalTime getEnd();

    LocalDate getStartAt();

    LocalDate getExpiredAt();

    LocalDateTime getPlayAt();

    String getMatchMakingStatusCode();
}
