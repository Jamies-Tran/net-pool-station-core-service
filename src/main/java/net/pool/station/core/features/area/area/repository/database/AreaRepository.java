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
        INNER JOIN StationSpaceEntity ss ON ss.stationSpaceId.stationId = a.stationId 
                AND ss.stationSpaceId.spaceId = a.spaceId
        INNER JOIN StationEntity s ON s.stationId = ss.stationSpaceId.stationId
        WHERE a.areaCode = :areaCode
              AND a.deleted = FALSE
        """)
    Boolean existsByAreaCode(String areaCode);

    Optional<AreaEntity> findByAreaIdAndDeletedFalse(Long areaId);

    @Query("""
        SELECT a
        FROM AreaEntity a
        INNER JOIN AreaTypeEntity at ON a.areaTypeId = at.areaTypeId
        WHERE a.deleted = FALSE
                AND (:#{#criteria.search().empty} = TRUE
                        OR a.areaName ILIKE %:#{#criteria.search()}%
                        OR a.areaCode ILIKE %:#{#criteria.search()}%)
                AND (:#{#criteria.typeCodes().empty} = TRUE
                        OR at.typeCode IN :#{#criteria.typeCodes()})
                AND (:#{#criteria.statusCodes().empty} = TRUE
                        OR a.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<AreaEntity> findAll(AreaCriteria criteria, Pageable pageable);
}
