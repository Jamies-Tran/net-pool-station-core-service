package net.pool.station.core.domain.station.resource.specs;

import net.pool.station.core.domain.DomainKey;

import java.util.Optional;

public interface StationResourceSpecUseCase {
    void save(StationResourceSpec stationResourceSpec);

    Optional<StationResourceSpec> findByAreaId(DomainKey<Long> areaId);

    void update(DomainKey<Long> stationResourceSpecId, StationResourceSpec stationResourceSpec);

    void delete(DomainKey<Long> stationResourceSpecId);
}
