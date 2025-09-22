package net.pool.station.core.features.station.space.repository.database;

import net.pool.station.core.domain.station.space.StationSpaceCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationSpaceRepository extends JpaRepository<StationSpaceEntity, Long> {
    Boolean existsByStationIdAndSpaceName(Long spaceId, String spaceName);

    Boolean existsByStationIdAndSpaceCode(Long spaceId, String spaceCode);

    Optional<StationSpaceEntity> findByStationSpaceIdAndDeletedFalse(Long stationSpaceId);

    @Query("""
        SELECT ss
        FROM StationSpaceEntity ss
        INNER JOIN SpaceEntity s ON ss.spaceId = s.spaceId
        WHERE ss.deleted = FALSE
                AND (:#{#criteria.search().empty} = TRUE
                        OR (ss.spaceName ILIKE %:#{#criteria.search()}%
                                OR ss.spaceCode ILIKE %:#{#criteria.search()}%))
                AND (:#{#criteria.stationId()} = 0
                        OR ss.stationId = :#{#criteria.stationId()})
                AND (:#{#criteria.typeCodes().empty} = TRUE
                        OR s.typeCode IN :#{#criteria.typeCodes()})
                AND (:#{#criteria.statusCodes().empty} = TRUE
                        OR s.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<StationSpaceEntity> findAll(StationSpaceCriteria criteria, Pageable pageable);
}
