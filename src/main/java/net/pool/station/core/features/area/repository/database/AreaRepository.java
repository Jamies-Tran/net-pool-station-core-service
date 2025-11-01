package net.pool.station.core.features.area.repository.database;

import net.pool.station.core.domain.area.AreaCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<AreaEntity, Long> {
    @Query("""
        SELECT COUNT(a) > 0
        FROM AreaEntity a
        INNER JOIN StationSpaceEntity ss ON a.stationSpaceId = ss.stationSpaceId
        INNER JOIN StationEntity s ON s.stationId = ss.stationId
        WHERE a.areaCode = :areaCode
              AND a.deleted = FALSE
        """)
    Boolean existsByAreaCode(String areaCode);

    Optional<AreaEntity> findByAreaIdAndDeletedFalse(Long areaId);

    @Query("""
        SELECT a
        FROM AreaEntity a
        INNER JOIN StationSpaceEntity ss ON a.stationSpaceId = ss.stationSpaceId
        WHERE a.deleted = FALSE
                AND (:#{#criteria.search().empty} = TRUE
                        OR a.areaName ILIKE %:#{#criteria.search()}%
                        OR a.areaCode = :#{#criteria.search()})
                AND (:#{#criteria.statusCodes().empty} = TRUE
                        OR a.statusCode IN :#{#criteria.statusCodes()})
                AND (:#{#criteria.stationId()} = 0
                        OR ss.stationId = :#{#criteria.stationId()})
                AND (:#{#criteria.spaceId()} = 0
                        OR ss.spaceId = :#{#criteria.spaceId()})
        """)
    Page<AreaEntity> findAll(AreaCriteria criteria, Pageable pageable);
}
