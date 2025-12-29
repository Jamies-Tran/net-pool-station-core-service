package net.pool.station.core.features.match.making.slot.respository.database.dao;

import java.time.LocalTime;

public interface MatchMakingSlotDao {
    MatchMakingSlotDaoId getId();

    LocalTime getBegin();

    LocalTime getEnd();
}
