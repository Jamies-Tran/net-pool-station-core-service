package net.pool.station.core.features.area.area.repository.database;

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
        INNER JOIN StationSpaceEntity ss ON ss.stationId = a.stationId 
                AND ss.spaceId = a.spaceId
        INNER JOIN StationEntity s ON s.stationId = ss.stationId
        WHERE a.areaCode = :areaCode
              AND a.deleted = FALSE
        """)
    Boolean existsByAreaCode(String areaCode);

    Optional<AreaEntity> findByAreaIdAndDeletedFalse(Long areaId);

    @Query("""
        SELECT a
        FROM AreaEntity a
        INNER JOIN AreaTypeEntity at ON a.areaTypeId = at.areaTypeId
        INNER JOIN StationSpaceEntity ss ON ss.stationId = a.stationId
                AND ss.spaceId = a.spaceId
        WHERE a.deleted = FALSE
                AND (:#{#criteria.search().empty} = TRUE
                        OR a.areaName ILIKE %:#{#criteria.search()}%
                        OR a.areaCode = :#{#criteria.search()})
                AND (:#{#criteria.typeCodes().empty} = TRUE
                        OR at.typeCode IN :#{#criteria.typeCodes()})
                AND (:#{#criteria.statusCodes().empty} = TRUE
                        OR a.statusCode IN :#{#criteria.statusCodes()})
                AND (:#{#criteria.stationId()} = 0
                        OR a.stationId = :#{#criteria.stationId()})
                AND (:#{#criteria.spaceId()} = 0
                        OR a.spaceId = :#{#criteria.spaceId()})
        """)
    Page<AreaEntity> findAll(AreaCriteria criteria, Pageable pageable);
}
