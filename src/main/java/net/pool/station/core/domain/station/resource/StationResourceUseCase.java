package net.pool.station.core.domain.station.resource;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface StationResourceUseCase {
    void save(StationResource stationResource);

    Page<StationResource> findAll(StationResourceCriteria criteria, PageRequest pageRequest);

    Optional<StationResource> findById(DomainKey<Long> stationResourceId);

    void update(DomainKey<Long> stationResourceId, StationResource stationResource);

    void enable(DomainKey<Long> stationResourceId);

    void disable(DomainKey<Long> stationResourceId);

    void delete(DomainKey<Long> stationResourceId);
}
