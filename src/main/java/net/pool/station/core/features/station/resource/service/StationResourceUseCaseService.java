package net.pool.station.core.features.station.resource.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EResourceStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.resource.StationResource;
import net.pool.station.core.domain.station.resource.StationResourceCriteria;
import net.pool.station.core.domain.station.resource.StationResourceUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceUseCaseService implements StationResourceUseCase {
    StationResourceCommandService commandService;

    StationResourceQueryService queryService;

    @Override
    @Transactional
    public void save(StationResource stationResource) {
        commandService.save(stationResource);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StationResource> findAll(StationResourceCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StationResource> findById(DomainKey<Long> stationResourceId) {
        return queryService.findById(stationResourceId.value());
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> stationResourceId, StationResource stationResource) {
        commandService.update(stationResourceId.value(), stationResource);
    }

    @Override
    @Transactional
    public void enable(DomainKey<Long> stationResourceId) {
        commandService.updateStatus(stationResourceId.value(), EResourceStatus.ENABLE);
    }

    @Override
    @Transactional
    public void disable(DomainKey<Long> stationResourceId) {
        commandService.updateStatus(stationResourceId.value(), EResourceStatus.DISABLE);
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> stationResourceId) {
        commandService.delete(stationResourceId.value());
    }
}
