package net.pool.station.core.features.station.space.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.station.space.StationSpace;
import net.pool.station.core.domain.station.space.StationSpaceCriteria;
import net.pool.station.core.domain.station.space.StationSpaceId;
import net.pool.station.core.features.station.space.repository.database.StationSpaceEntityMapper;
import net.pool.station.core.features.station.space.repository.database.StationSpaceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceQueryService {
    StationSpaceRepository repository;

    StationSpaceEntityMapper mapper;

    protected Optional<StationSpace> findById(StationSpaceId stationSpaceId) {
        return repository.findById(stationSpaceId)
                .map(mapper::toDto);
    }

    protected Page<StationSpace> findAll(StationSpaceCriteria criteria, Pageable pageable) {
        return repository.findAll(criteria, pageable)
                .map(mapper::toDto);
    }
}
