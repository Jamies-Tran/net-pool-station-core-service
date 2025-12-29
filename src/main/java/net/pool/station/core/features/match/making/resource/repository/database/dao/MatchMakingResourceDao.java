package net.pool.station.core.features.match.making.resource.repository.database.dao;

import net.pool.station.core.features.match.making.resource.repository.database.MatchMakingResourceEntity;

public interface MatchMakingResourceDao {
    MatchMakingResourceDaoId getId();

    String getTypeCode();

    String getTypeName();

    Integer getPrice();
}
