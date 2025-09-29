package net.pool.station.core.features.area.type.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.area.type.AreaType;
import net.pool.station.core.domain.area.type.AreaTypeCriteria;
import net.pool.station.core.features.area.type.repository.database.AreaTypeEntityMapper;
import net.pool.station.core.features.area.type.repository.database.AreaTypeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreaTypeQueryService {
    AreaTypeRepository repository;

    AreaTypeEntityMapper mapper;

    protected Optional<AreaType> findById(Long areaTypeId) {
        return repository.findByAreaTypeIdAndDeletedFalse(areaTypeId)
                .map(mapper::toDto);
    }

    protected Page<AreaType> findAll(AreaTypeCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }
}
