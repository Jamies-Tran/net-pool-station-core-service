package net.pool.station.core.features.station.resource.specs.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpecCriteria;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpecUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceSpecUseCaseService implements StationResourceSpecUseCase {
    StationResourceSpecCommandService commandService;

    StationResourceSpecQueryService queryService;

    @Override
    @Transactional
    public void save(StationResourceSpec stationResourceSpec) {
        commandService.save(stationResourceSpec);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StationResourceSpec> findByStationResourceId(DomainKey<Long> stationResourceId) {
        return queryService.findByStationResourceId(stationResourceId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public List<StationResourceSpec> findAll(StationResourceSpecCriteria criteria) {
        return queryService.findAll(criteria);
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> stationResourceSpecId) {
        commandService.delete(stationResourceSpecId.value());
    }
}
