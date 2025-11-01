package net.pool.station.core.features.station.resource.specs.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import net.pool.station.core.features.station.resource.specs.repository.database.StationResourceSpecEntityMapper;
import net.pool.station.core.features.station.resource.specs.repository.database.StationResourceSpecRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceSpecQueryService {
    StationResourceSpecRepository repository;

    StationResourceSpecEntityMapper mapper;

    protected Optional<StationResourceSpec> findByStationResourceId(Long stationResourceId) {
        return repository.findByStationResourceIdAndDeletedFalse(stationResourceId)
                .map(mapper::toDto);
    }
}
