package net.pool.station.core.features.station.resource.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.station.resource.StationResource;
import net.pool.station.core.domain.station.resource.StationResourceCriteria;
import net.pool.station.core.features.station.resource.repository.database.StationResourceEntityMapper;
import net.pool.station.core.features.station.resource.repository.database.StationResourceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceQueryService {
    StationResourceRepository repository;

    StationResourceEntityMapper mapper;

    protected Optional<StationResource> findById(Long stationResourceId) {
        return repository.findByStationResourceIdAndDeletedFalse(stationResourceId)
                .map(mapper::toDto);
    }

    protected Page<StationResource> findAll(StationResourceCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }
}
