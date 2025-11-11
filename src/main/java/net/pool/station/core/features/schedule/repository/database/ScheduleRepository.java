package net.pool.station.core.features.schedule.repository.database;

import net.pool.station.core.domain.schedule.ScheduleCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
        SELECT sc
        FROM StationSpaceEntity sp
        LEFT JOIN StationSpaceScheduleEntity ss ON ss.stationSpaceId = sp.stationSpaceId
        LEFT JOIN StationEntity st ON sp.stationId = st.stationId
        LEFT JOIN ScheduleEntity sc ON sp.stationId = sc.stationId AND ss IS NULL
        """)
    Page<ScheduleEntity> findAllByStationResource(ScheduleCriteria criteria, Pageable pageable);
}
