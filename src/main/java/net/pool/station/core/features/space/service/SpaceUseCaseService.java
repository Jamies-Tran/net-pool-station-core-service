package net.pool.station.core.features.space.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.ESpaceStatus;
import net.pool.station.core.domain.DomainCode;
import net.pool.station.core.domain.space.Space;
import net.pool.station.core.domain.space.SpaceCriteria;
import net.pool.station.core.domain.space.SpaceUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpaceUseCaseService implements SpaceUseCase {
    SpaceCommandService commandService;

    SpaceQueryService queryService;

    @Override
    @Transactional
    public void save(Space space) {
        commandService.save(space);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Space> findById(DomainCode<Long> spaceId) {
        return queryService.findById(spaceId.value());
    }

    @Override
    @Transactional
    public void update(DomainCode<Long> spaceId, Space space) {
        commandService.update(spaceId.value(), space);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Space> findAll(SpaceCriteria criteria, Pageable pageable) {
        return queryService.findAll(criteria, pageable);
    }

    @Override
    @Transactional
    public void delete(DomainCode<Long> spaceId) {
        commandService.delete(spaceId.value());
    }

    @Override
    @Transactional
    public void enable(DomainCode<Long> spaceId) {
        commandService.updateStatus(spaceId.value(), ESpaceStatus.ACTIVE);
    }

    @Override
    @Transactional
    public void disable(DomainCode<Long> spaceId) {
        commandService.updateStatus(spaceId.value(), ESpaceStatus.INACTIVE);
    }
}
