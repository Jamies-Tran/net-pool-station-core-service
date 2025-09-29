package net.pool.station.core.features.area.area.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.area.Area;
import net.pool.station.core.domain.area.AreaCriteria;
import net.pool.station.core.features.area.area.repository.database.AreaEntityMapper;
import net.pool.station.core.features.area.area.repository.database.AreaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreaQueryService {
    AreaRepository repository;

    AreaEntityMapper mapper;

    protected Optional<Area> findById(Long id) {
        return repository.findByAreaIdAndDeletedFalse(id)
                .map(mapper::toDto);
    }

    protected Page<Area> findAll(AreaCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }
}
