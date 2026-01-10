package net.pool.station.core.features.timeslot.repository.database;

import net.pool.station.core.domain.timeslot.TimeSlotCriteria;
import net.pool.station.core.features.timeslot.repository.database.dao.TimeSlotAllowBookingDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlotEntity, Long> {
    @Query("""
        SELECT t
        FROM TimeSlotEntity t
        WHERE t.scheduleId = :#{#criteria.scheduleId()}
            AND (:#{#criteria.periodCodes().empty} = TRUE
                    OR t.periodCode IN :#{#criteria.periodCodes()})
            AND (:#{#criteria.statusCodes().empty} = TRUE
                    OR t.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<TimeSlotEntity> findAll(TimeSlotCriteria criteria, Pageable pageable);

    List<TimeSlotEntity> findAllByScheduleIdAndStatusCode(Long scheduleId, String statusCode);

    List<TimeSlotEntity> findAllByScheduleId(Long scheduleId);

    void deleteAllByScheduleId(Long scheduleId);

    @Query("""
        SELECT DISTINCT
                t.timeSlotId AS timeSlotId,
                CASE 
                    WHEN (b IS NOT NULL AND b.statusCode IN ('PENDING', 'NEW', 'PROCESSING') 
                            AND bs IS NOT NULL AND t.timeSlotId = bs.bookingSlotId.timeSlotId) OR
                         (m IS NOT NULL AND m.statusCode IN ('PENDING', 'STARTED', 'DRAFT') 
                                 AND ms IS NOT NULL AND t.timeSlotId = ms.id.timeSlotId) THEN FALSE 
                    WHEN (bs IS NULL AND (sc.date <= CURRENT_DATE AND t.end < CURRENT_TIME)) THEN FALSE
                    ELSE TRUE 
                END AS allowBooking      
        FROM StationResourceEntity sr
        INNER JOIN AreaEntity a ON a.areaId = sr.areaId
        INNER JOIN StationSpaceEntity ss ON ss.stationSpaceId = a.stationSpaceId
        LEFT JOIN StationEntity s ON ss.stationId = s.stationId
        LEFT JOIN ScheduleEntity sc ON sc.stationId = s.stationId AND sc.scheduleId = :scheduleId AND sc.deleted = FALSE
        LEFT JOIN BookingEntity b ON sc.scheduleId = b.scheduleId AND sc.deleted = FALSE 
        LEFT JOIN TimeSlotEntity t ON sc.scheduleId = t.scheduleId
        LEFT JOIN BookingSlotEntity bs ON bs.bookingSlotId.timeSlotId = t.timeSlotId
        LEFT JOIN MatchMakingEntity m ON m.scheduleId = sc.scheduleId AND m.deleted = FALSE
        LEFT JOIN MatchMakingSlotEntity ms ON m.matchMakingId = ms.id.matchMakingId
        WHERE sr.stationResourceId = :stationResourceId
        """)
    List<TimeSlotAllowBookingDao> findAllByScheduleIdAndStationResourceId(Long scheduleId, Long stationResourceId);

    List<TimeSlotEntity> findListByTimeSlotIdIn(List<Long> timeSlotIds);
}
