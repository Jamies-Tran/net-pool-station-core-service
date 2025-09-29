package net.pool.station.core.domain.area;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface AreaUseCase {
    void save(Area area);

    Optional<Area> findById(DomainKey<Long> areaId);

    Page<Area> findAll(AreaCriteria criteria, PageRequest pageRequest);

    void update(DomainKey<Long> areaId, Area area);

    void enable(DomainKey<Long> areaId);

    void disable(DomainKey<Long> areaId);

    void delete(DomainKey<Long> areaId);
}
