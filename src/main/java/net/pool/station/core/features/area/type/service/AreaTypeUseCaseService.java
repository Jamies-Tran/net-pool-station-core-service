package net.pool.station.core.features.area.type.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EAreaTypeStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.area.type.AreaType;
import net.pool.station.core.domain.area.type.AreaTypeCriteria;
import net.pool.station.core.domain.area.type.AreaTypeUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreaTypeUseCaseService implements AreaTypeUseCase {
    AreaTypeCommandService commandService;

    AreaTypeQueryService queryService;

    @Override
    @Transactional
    public void save(AreaType areaType) {
        commandService.save(areaType);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AreaType> findById(DomainKey<Long> areaTypeId) {
        return queryService.findById(areaTypeId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AreaType> findAll(AreaTypeCriteria criteria, PageRequest pageable) {
        return queryService.findAll(criteria, pageable);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> areaTypeId, AreaType areaType) {
        commandService.update(areaTypeId.value(), areaType);
    }

    @Override
    @Transactional
    public void active(DomainKey<Long> areaTypeId) {
        commandService.updateStatus(areaTypeId.value(), EAreaTypeStatus.ACTIVE);
    }

    @Override
    @Transactional
    public void inactive(DomainKey<Long> areaTypeId) {
        commandService.updateStatus(areaTypeId.value(), EAreaTypeStatus.INACTIVE);
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> areaTypeId) {
        commandService.delete(areaTypeId.value());
    }
}
