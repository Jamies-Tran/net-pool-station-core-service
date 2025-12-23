package net.pool.station.core.features.station.station.repository.database.dao;

import net.pool.station.core.features.station.station.repository.database.StationEntity;

public interface StationDao {
    Long getStationId();

    StationEntity getStation();

    Double getDistance();

}
