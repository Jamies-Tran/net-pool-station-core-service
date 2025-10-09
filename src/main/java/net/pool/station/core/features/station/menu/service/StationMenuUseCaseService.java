package net.pool.station.core.features.station.menu.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EMenuStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.menu.StationMenu;
import net.pool.station.core.domain.station.menu.StationMenuCriteria;
import net.pool.station.core.domain.station.menu.StationMenuUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationMenuUseCaseService implements StationMenuUseCase {
    StationMenuCommandService commandService;

    StationMenuQueryService queryService;

    @Override
    @Transactional
    public void save(StationMenu stationMenu) {
        commandService.save(stationMenu);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StationMenu> findById(DomainKey<Long> stationMenuId) {
        return queryService.findById(stationMenuId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StationMenu> findAll(StationMenuCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> stationMenuId, StationMenu stationMenu) {
        commandService.update(stationMenuId.value(), stationMenu);
    }

    @Override
    @Transactional
    public void enable(DomainKey<Long> stationMenuId) {
        commandService.updateStatus(stationMenuId.value(), EMenuStatus.ENABLE);
    }

    @Override
    @Transactional
    public void disable(DomainKey<Long> stationMenuId) {
        commandService.updateStatus(stationMenuId.value(), EMenuStatus.DISABLE);
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> stationMenuId) {
        commandService.delete(stationMenuId.value());
    }
}
