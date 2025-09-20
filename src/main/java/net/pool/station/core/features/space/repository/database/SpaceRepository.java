package net.pool.station.core.features.space.repository.database;

import net.pool.station.core.domain.space.SpaceCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpaceRepository extends JpaRepository<SpaceEntity, Long> {
    Boolean existsByStationIdAndSpaceName(Long stationId, String spaceName);

    @Query("""
        SELECT s
        FROM SpaceEntity s
        WHERE s.deleted = FALSE
                AND s.spaceId = :spaceId
        """)
    Optional<SpaceEntity> findBySpaceId(Long spaceId);

    @Query("""
        SELECT s
        FROM SpaceEntity s
        WHERE s.deleted = FALSE
                AND (:#{#criteria.stationId()} = 0
                        OR s.stationId = :#{#criteria.stationId()})
                AND (:#{#criteria.search().empty} = TRUE
                        OR (s.spaceName ILIKE %:#{#criteria.search()}%
                                OR s.spaceCode ILIKE %:#{#criteria.search()}%))
                AND (:#{#criteria.typeCodes().empty} = TRUE
                        OR s.typeCode IN :#{#criteria.typeCodes()})
                
        """)
    Page<SpaceEntity> findAll(SpaceCriteria criteria, Pageable pageable);
}
