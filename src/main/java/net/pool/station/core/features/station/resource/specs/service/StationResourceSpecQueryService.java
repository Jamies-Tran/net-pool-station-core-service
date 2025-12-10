package net.pool.station.core.features.station.resource.specs.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpecCriteria;
import net.pool.station.core.features.station.resource.specs.repository.database.StationResourceSpecEntity;
import net.pool.station.core.features.station.resource.specs.repository.database.StationResourceSpecEntityMapper;
import net.pool.station.core.features.station.resource.specs.repository.database.StationResourceSpecRepository;
import net.pool.station.core.features.station.resource.specs.repository.database.dao.StationResourceSpecDao;
import net.pool.station.core.features.station.resource.specs.repository.database.dao.StationResourceSpecDaoMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceSpecQueryService {
    StationResourceSpecRepository repository;

    StationResourceSpecEntityMapper mapper;

    StationResourceSpecDaoMapper daoMapper;

    protected Optional<StationResourceSpec> findByStationResourceId(Long stationResourceId) {
        return repository.findByStationResourceIdAndDeletedFalse(stationResourceId)
                .map(mapper::toDto);
    }

    protected List<StationResourceSpec> findAll(StationResourceSpecCriteria criteria) {
        List<StationResourceSpecDao> specs = repository.findAll(criteria);

        return daoMapper.toDto(specs);
    }
}
