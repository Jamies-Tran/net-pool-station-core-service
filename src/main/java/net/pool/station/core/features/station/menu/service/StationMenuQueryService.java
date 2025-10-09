package net.pool.station.core.features.station.menu.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.station.menu.StationMenu;
import net.pool.station.core.domain.station.menu.StationMenuCriteria;
import net.pool.station.core.features.station.menu.repository.database.StationMenuEntityMapper;
import net.pool.station.core.features.station.menu.repository.database.StationMenuRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationMenuQueryService {
    StationMenuRepository repository;

    StationMenuEntityMapper mapper;

    protected Optional<StationMenu> findById(Long stationMenuId) {
        return repository.findByStationMenuIdAndDeletedFalse(stationMenuId)
                .map(mapper::toDto);
    }

    protected Page<StationMenu> findAll(StationMenuCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria.specification(), pageRequest)
                .map(mapper::toDto);
    }
}
