package net.pool.station.core.features.schedule.repository.database;

import net.pool.station.core.domain.schedule.ScheduleCriteria;
import net.pool.station.core.features.schedule.repository.database.models.ScheduleDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    Boolean existsByDateAndStationId(LocalDate date, Long stationId);

    Optional<ScheduleEntity> findByScheduleIdAndDeletedFalse(Long scheduleId);

    @Query("""
        SELECT sc
        FROM ScheduleEntity sc
        INNER JOIN StationEntity st ON sc.stationId = st.stationId
        WHERE sc.deleted = FALSE
                AND (st.stationId = :#{#criteria.stationId()})
                AND (:#{#criteria.dateRange().empty} = TRUE
                        OR sc.date BETWEEN :#{#criteria.startFrom()}
                                AND :#{#criteria.endTo()})
                AND (:#{#criteria.statusCodes().empty} = TRUE
                        OR sc.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<ScheduleEntity> findAllByStation(ScheduleCriteria criteria, Pageable pageable);

    @Query("""
        SELECT DISTINCT COALESCE(sc2.scheduleId, sc1.scheduleId) AS scheduleId
        FROM ScheduleEntity sc1
        INNER JOIN StationEntity st ON sc1.stationId = st.stationId
        INNER JOIN StationSpaceEntity sp ON st.stationId = sp.stationId
        LEFT JOIN StationSpaceScheduleEntity sss ON sp.stationSpaceId = sss.stationSpaceId
        LEFT JOIN ScheduleEntity sc2 ON sss.scheduleId = sc2.scheduleId
        INNER JOIN AreaEntity a ON a.stationSpaceId = sp.stationSpaceId
        INNER JOIN StationResourceEntity sr ON sr.areaId = a.areaId
        WHERE sr.stationResourceId = :#{#criteria.stationResourceId()}
            AND (COALESCE(sc2.date, sc1.date) BETWEEN :#{#criteria.startFrom()}
                AND :#{#criteria.endTo()})
            AND (:#{#criteria.statusCodes().empty} = TRUE
                    OR COALESCE(sc2.statusCode, sc1.statusCode) IN :#{#criteria.statusCodes()} )
        """)
    Page<Long> findAllByStationResource(ScheduleCriteria criteria, Pageable pageable);

    List<ScheduleEntity> findAllByScheduleIdIn(List<Long> scheduleIds);
}
