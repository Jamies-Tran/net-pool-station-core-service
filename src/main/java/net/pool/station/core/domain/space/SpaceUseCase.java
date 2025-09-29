package net.pool.station.core.domain.space;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SpaceUseCase {
    void save(Space space);

    Optional<Space> findById(DomainKey<Long> spaceId);

    void update(DomainKey<Long> spaceId, Space space);

    Page<Space> findAll(SpaceCriteria criteria, PageRequest pageRequest);

    void delete(DomainKey<Long> spaceId);

    void enable(DomainKey<Long> spaceId);

    void disable(DomainKey<Long> spaceId);
}
