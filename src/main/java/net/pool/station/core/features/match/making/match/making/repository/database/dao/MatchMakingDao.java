package net.pool.station.core.features.match.making.match.making.repository.database.dao;

import net.pool.station.core.features.match.making.match.making.repository.database.MatchMakingEntity;

public interface MatchMakingDao {
    Long getMatchMakingId();

    MatchMakingEntity getMatchMaking();

    Boolean getAllowView();

    Boolean getAllowJoin();
}
