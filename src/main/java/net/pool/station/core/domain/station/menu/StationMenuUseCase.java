package net.pool.station.core.domain.station.menu;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface StationMenuUseCase {
    void save(StationMenu stationMenu);

    Optional<StationMenu> findById(DomainKey<Long> stationMenuId);

    Page<StationMenu> findAll(StationMenuCriteria criteria, PageRequest pageRequest);

    void update(DomainKey<Long> stationMenuId, StationMenu stationMenu);

    void enable(DomainKey<Long> stationMenuId);

    void disable(DomainKey<Long> stationMenuId);

    void delete(DomainKey<Long> stationMenuId);

}
