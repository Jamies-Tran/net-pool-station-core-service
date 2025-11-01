package net.pool.station.core.features.area.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EAreaStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.area.Area;
import net.pool.station.core.domain.area.AreaCriteria;
import net.pool.station.core.domain.area.AreaUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreaUseCaseService implements AreaUseCase {
    AreaCommandService commandService;

    AreaQueryService queryService;

    @Override
    @Transactional
    public void save(Area area) {
        commandService.save(area);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Area> findById(DomainKey<Long> areaId) {
        return queryService.findById(areaId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Area> findAll(AreaCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> areaId, Area area) {
        commandService.update(areaId.value(), area);
    }

    @Override
    @Transactional
    public void enable(DomainKey<Long> areaId) {
        commandService.updateStatus(areaId.value(), EAreaStatus.ACTIVE);
    }

    @Override
    @Transactional
    public void disable(DomainKey<Long> areaId) {
        commandService.updateStatus(areaId.value(), EAreaStatus.INACTIVE);
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> areaId) {
        commandService.delete(areaId.value());
    }
}
