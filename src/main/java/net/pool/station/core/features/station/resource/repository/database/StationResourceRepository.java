package net.pool.station.core.features.station.resource.repository.database;

import net.pool.station.core.domain.station.resource.StationResourceCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationResourceRepository extends JpaRepository<StationResourceEntity, Long> {
    Boolean existsByAreaIdAndResourceCodeAndDeletedFalse(Long stationId, String resourceCode);

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
}
