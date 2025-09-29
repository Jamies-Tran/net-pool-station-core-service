package net.pool.station.core.features.station.space.repository.database;

import net.pool.station.core.domain.station.space.StationSpaceCriteria;
import net.pool.station.core.domain.station.space.StationSpaceId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationSpaceRepository extends JpaRepository<StationSpaceEntity, Long> {
    @Query("""
        SELECT COUNT(ss) > 0
        FROM StationSpaceEntity ss
        WHERE ss.deleted = FALSE 
                AND ss.stationSpaceId.spaceId = :spaceId
                AND ss.spaceName = :spaceName
        """)
    Boolean existsByStationIdAndSpaceName(Long spaceId, String spaceName);

    @Query("""
        SELECT COUNT(ss) > 0
        FROM StationSpaceEntity ss
        WHERE ss.deleted = FALSE
                AND ss.stationSpaceId.spaceId = :spaceId
                AND ss.spaceCode = :spaceCode
        """)
    Boolean existsByStationIdAndSpaceCode(Long spaceId, String spaceCode);

    @Query("""
        SELECT ss
        FROM StationSpaceEntity ss
        WHERE ss.deleted = FALSE
                AND ss.stationSpaceId.stationId = :#{#stationSpaceId.stationId()}
                AND ss.stationSpaceId.spaceId = :#{#stationSpaceId.spaceId()}
        """)
    Optional<StationSpaceEntity> findById(StationSpaceId stationSpaceId);

    @Query("""
        SELECT ss
        FROM StationSpaceEntity ss
        INNER JOIN SpaceEntity s ON ss.stationSpaceId.spaceId = s.spaceId
        WHERE ss.deleted = FALSE
                AND (:#{#criteria.search().empty} = TRUE
                        OR (ss.spaceName ILIKE %:#{#criteria.search()}%
                                OR ss.spaceCode ILIKE %:#{#criteria.search()}%))
                AND (:#{#criteria.stationId()} = 0
                        OR ss.stationSpaceId.stationId = :#{#criteria.stationId()})
                AND (:#{#criteria.typeCodes().empty} = TRUE
                        OR s.typeCode IN :#{#criteria.typeCodes()})
                AND (:#{#criteria.statusCodes().empty} = TRUE
                        OR s.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<StationSpaceEntity> findAll(StationSpaceCriteria criteria, Pageable pageable);
}
