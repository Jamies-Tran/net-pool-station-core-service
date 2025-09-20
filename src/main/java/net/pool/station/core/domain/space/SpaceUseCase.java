package net.pool.station.core.domain.space;

import net.pool.station.core.domain.DomainCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SpaceUseCase {
    void save(Space space);

    Optional<Space> findById(DomainCode<Long> spaceId);

    void update(DomainCode<Long> spaceId, Space space);

    Page<Space> findAll(SpaceCriteria criteria, Pageable pageable);

    void delete(DomainCode<Long> spaceId);

    void enable(DomainCode<Long> spaceId);

    void disable(DomainCode<Long> spaceId);
}
