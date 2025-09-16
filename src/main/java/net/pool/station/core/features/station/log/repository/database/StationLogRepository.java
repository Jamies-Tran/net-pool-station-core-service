package net.pool.station.core.features.station.log.repository.database;

import net.pool.station.core.domain.station.log.StationLogCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StationLogRepository extends JpaRepository<StationLogEntity, Long> {
    @Query("""
        SELECT s
        FROM StationLogEntity s
        WHERE s.stationId = :#{#criteria.stationId()}
            AND (s.createdAt BETWEEN :#{#criteria.timeRange().get(0)}
                AND :#{#criteria.timeRange().get(1)})
            AND (:#{#criteria.search().empty} = TRUE
                    OR s.logTypeName ILIKE %:#{#criteria.search()}%)
            AND (:#{#criteria.logTypeCodes().empty} = TRUE
                    OR s.logTypeCode IN :#{#criteria.logTypeCodes()})
        """)
    Page<StationLogEntity> findAll(StationLogCriteria criteria, Pageable pageable);
}
