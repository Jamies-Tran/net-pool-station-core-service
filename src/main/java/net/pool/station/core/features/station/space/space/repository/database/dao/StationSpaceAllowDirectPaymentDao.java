package net.pool.station.core.features.station.space.space.repository.database.dao;

import net.pool.station.core.features.station.space.space.repository.database.StationSpaceEntity;

public interface StationSpaceAllowDirectPaymentDao {
    StationSpaceEntity getStationSpace();

    Boolean getAllowDirectPayment();
}
