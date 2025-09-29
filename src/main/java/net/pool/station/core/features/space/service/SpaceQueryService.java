package net.pool.station.core.features.space.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.space.Space;
import net.pool.station.core.domain.space.SpaceCriteria;
import net.pool.station.core.features.space.repository.database.SpaceEntityMapper;
import net.pool.station.core.features.space.repository.database.SpaceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpaceQueryService {
    SpaceRepository repository;

    SpaceEntityMapper mapper;

    protected Optional<Space> findById(Long spaceId) {
        return repository.findBySpaceId(spaceId)
                .map(mapper::toDto);
    }

    protected Page<Space> findAll(SpaceCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }

}
