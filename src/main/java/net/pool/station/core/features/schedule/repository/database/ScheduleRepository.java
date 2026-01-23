package net.pool.station.core.features.schedule.repository.database;

import net.pool.station.core.domain.schedule.ScheduleCriteria;
import net.pool.station.core.features.schedule.repository.database.models.ScheduleDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    Boolean existsByDateAndStationId(LocalDate date, Long stationId);

    @Query("""
        SELECT COUNT(s) > 0
        FROM ScheduleEntity s
        INNER JOIN BookingEntity b ON s.scheduleId = b.scheduleId AND b.deleted = FALSE
        INNER JOIN MatchMakingEntity m ON s.scheduleId = m.scheduleId AND m.deleted = FALSE
        """)
    Boolean existsInBookingOrMatchMaking(Long scheduleId);

    @Query("""
        SELECT
                (COUNT(b) = 0 AND COUNT(m) = 0) AS allowUpdate,
                s.scheduleId AS scheduleId
        FROM ScheduleEntity s
        LEFT JOIN BookingEntity b ON s.scheduleId = b.scheduleId AND b.deleted = FALSE
        LEFT JOIN MatchMakingEntity m ON s.scheduleId = m.scheduleId AND m.deleted = FALSE
        WHERE s.scheduleId IN :scheduleIds
        GROUP BY s.scheduleId
        """)
    List<ScheduleDao> findAllWithAllowUpdateIn(List<Long> scheduleIds);

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
        SELECT DISTINCT sc.scheduleId AS scheduleId
        FROM ScheduleEntity sc
        INNER JOIN StationEntity st ON sc.stationId = st.stationId
        INNER JOIN StationSpaceEntity sp ON st.stationId = sp.stationId
        INNER JOIN AreaEntity a ON a.stationSpaceId = sp.stationSpaceId
        INNER JOIN StationResourceEntity sr ON sr.areaId = a.areaId
        WHERE sr.stationResourceId = :#{#criteria.stationResourceId()}
            AND (sc.date BETWEEN :#{#criteria.startFrom()}
                AND :#{#criteria.endTo()})
            AND (:#{#criteria.statusCodes().empty} = TRUE
                    OR sc.statusCode IN :#{#criteria.statusCodes()} )
        """)
    Page<Long> findAllByStationResource(ScheduleCriteria criteria, Pageable pageable);

    @Query("""
        SELECT DISTINCT sc.scheduleId AS scheduleId
        FROM ScheduleEntity sc
        INNER JOIN StationEntity st ON sc.stationId = st.stationId
        INNER JOIN StationSpaceEntity sp ON st.stationId = sp.stationId
        WHERE sp.stationSpaceId = :#{#criteria.stationSpaceId()}
            AND (sc.date BETWEEN :#{#criteria.startFrom()}
                AND :#{#criteria.endTo()})
            AND (:#{#criteria.statusCodes().empty} = TRUE
                    OR sc.statusCode IN :#{#criteria.statusCodes()} )
        """)
    Page<Long> findAllByStationSpace(ScheduleCriteria criteria, Pageable pageable);

    List<ScheduleEntity> findAllByScheduleIdIn(List<Long> scheduleIds, Sort sort);

    List<ScheduleEntity> findAllByScheduleIdIn(List<Long> scheduleIds);

    @Query("""
        SELECT DISTINCT sc.scheduleId
        FROM ScheduleEntity sc
        LEFT JOIN BookingEntity b
                ON sc.scheduleId = b.scheduleId
                AND b.stationResourceId IN :stationResourceId
                AND sc.date >= :date
                        
        LEFT JOIN BookingSlotEntity bs
                ON b.bookingId = bs.bookingSlotId.bookingId
        
        LEFT JOIN TimeSlotEntity bts
                ON bts.timeSlotId = bs.bookingSlotId.timeSlotId
                AND :#{#time.get(0)} < bts.end
                AND :#{#time.get(1)} > bts.begin 
                        
        LEFT JOIN MatchScheduleEntity msc
                ON msc.id.scheduleId = sc.scheduleId
                AND sc.date >= :date
                        
        LEFT JOIN MatchMakingResourceEntity mr
                ON mr.id.matchMakingId = msc.id.matchMakingId
                AND mr.id.stationResourceId IN :stationResourceId
        
        LEFT JOIN MatchMakingSlotEntity ms
                ON mr.id.matchMakingId = ms.id.matchMakingId
                        
        LEFT JOIN TimeSlotEntity mts
                ON ms.id.timeSlotId = mts.timeSlotId
                AND :#{#time.get(0)} < mts.end
                AND :#{#time.get(1)} > mts.begin 
                       
        WHERE sc.stationId = :stationId 
                        AND sc.statusCode = :statusCode
                                AND sc.deleted = FALSE
                                        AND (mts IS NULL AND bts IS NULL)
        """)
    List<Long> findAvailableSchedule(Long stationId,
                                     LocalDate date,
                                     List<Long> stationResourceId,
                                     List<LocalTime> time,
                                     String statusCode);

    List<ScheduleEntity> findAllByScheduleIdInAndDeletedFalse(List<Long> scheduleIds);
}
