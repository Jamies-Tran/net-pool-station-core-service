package net.pool.station.core.features.station.resource.resource.repository.database;

import net.pool.station.core.domain.station.resource.StationResourceCriteria;
import net.pool.station.core.features.station.resource.resource.repository.database.dao.StationResourceDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationResourceRepository extends JpaRepository<StationResourceEntity, Long> {
    Boolean existsByAreaIdAndResourceCodeAndDeletedFalse(Long stationId, String resourceCode);

    @Query("""
        SELECT
                sr.stationResourceId AS stationResourceId,
                sr.areaId AS areaId,
                sr.resourceCode AS resourceCode,
                sr.resourceName AS resourceName,
                sr.typeCode AS typeCode,
                sr.typeName AS typeName,
                sr.statusCode AS statusCode,
                sr.statusName AS statusName,
                a.price AS price
        FROM StationResourceEntity sr
        INNER JOIN AreaEntity a ON sr.areaId = a.areaId
        WHERE sr.deleted = FALSE AND sr.stationResourceId = :stationResourceId
        """)
    Optional<StationResourceDao> findByStationResourceId(Long stationResourceId);

    Optional<StationResourceEntity> findByStationResourceIdAndDeletedFalse(Long stationResourceId);

    @Query("""
        SELECT s
        FROM StationResourceEntity s
        INNER JOIN AreaEntity a ON a.areaId = s.areaId
        WHERE (:#{#criteria.areaId()} = 0
                OR s.areaId = :#{#criteria.areaId()})
              AND (:#{#criteria.search().empty} = TRUE
                      OR (s.resourceName ILIKE %:#{#criteria.search()}%
                              OR s.resourceCode = :#{#criteria.search()}))
              AND (:#{#criteria.typeCodes().empty} = TRUE
                      OR s.typeCode IN :#{#criteria.typeCodes()})
              AND (:#{#criteria.statusCodes().empty} = TRUE
                      OR s.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<StationResourceEntity> findAll(StationResourceCriteria criteria, Pageable pageable);

    @Query("""
        SELECT COUNT(a) > 0
        FROM AreaEntity a
        WHERE a.areaId = :areaId
        """)
    Boolean validateToken(Long areaId);
}
