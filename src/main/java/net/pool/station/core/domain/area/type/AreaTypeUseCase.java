package net.pool.station.core.domain.area.type;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public interface AreaTypeUseCase {
    void save(AreaType areaType);

    Optional<AreaType> findById(DomainKey<Long> areaTypeId);

    Page<AreaType> findAll(AreaTypeCriteria criteria, PageRequest pageRequest);

    void update(DomainKey<Long> areaTypeId, AreaType areaType);

    void active(DomainKey<Long> areaTypeId);

    void inactive(DomainKey<Long> areaTypeId);

    void delete(DomainKey<Long> areaTypeId);
}
