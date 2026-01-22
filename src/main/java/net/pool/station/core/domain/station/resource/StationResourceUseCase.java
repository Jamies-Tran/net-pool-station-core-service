package net.pool.station.core.domain.station.resource;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface StationResourceUseCase {
    void save(StationResource stationResource);

    void save(DomainKey<Long> areaId, List<StationResource> stationResources);

    void saveWithSocketToken(String token, StationResource stationResource);

    Page<StationResource> findAll(StationResourceCriteria criteria, PageRequest pageRequest);

    Map<Row, List<StationResource>> findAllMapByRow(StationResourceCriteria criteria, PageRequest pageRequest);

    Optional<StationResource> findById(DomainKey<Long> stationResourceId);

    void update(DomainKey<Long> stationResourceId, StationResource stationResource);

    void enable(DomainKey<Long> stationResourceId);

    void disable(DomainKey<Long> stationResourceId);

    void delete(DomainKey<Long> stationResourceId);

    Integer totalPriceByStationResourceIdIn(List<Long> stationResourceIds);
}
