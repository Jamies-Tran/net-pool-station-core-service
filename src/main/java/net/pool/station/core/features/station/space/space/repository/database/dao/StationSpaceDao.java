package net.pool.station.core.features.station.space.space.repository.database.dao;

import net.pool.station.core.features.station.space.space.repository.database.StationSpaceEntity;

import java.util.Map;

public interface StationSpaceDao {
    StationSpaceEntity getStationSpace();

    Map<String, Object> getMetadata();
}
