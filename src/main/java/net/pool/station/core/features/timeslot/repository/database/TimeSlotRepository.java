package net.pool.station.core.features.timeslot.repository.database;

import net.pool.station.core.domain.timeslot.TimeSlotCriteria;
import net.pool.station.core.features.timeslot.repository.database.dao.TimeSlotAllowBookingDao;
import net.pool.station.core.features.timeslot.repository.database.dao.TimeSlotDao;
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

    @Query("""
        SELECT
                t.timeSlotId AS timeSlotId,
                t.scheduleId AS scheduleId,
                t.begin AS begin,
                t.end AS end,
                t.periodCode AS periodCode,
                t.periodName AS periodName,
                t.statusCode AS statusCode,
                t.statusName AS statusName,
                sc.date AS date
        FROM TimeSlotEntity t
        INNER JOIN ScheduleEntity sc ON t.scheduleId = sc.scheduleId
        WHERE t.scheduleId = :scheduleId
        """)
    List<TimeSlotDao> findAllByScheduleId(Long scheduleId);

    void deleteAllByScheduleId(Long scheduleId);

    @Query("""
        SELECT DISTINCT
                t.timeSlotId AS timeSlotId,
                CASE 
                    WHEN b IS NOT NULL AND b.statusCode IN ('PENDING', 'NEW', 'PROCESSING') 
                            AND bs IS NOT NULL AND t.timeSlotId = bs.bookingSlotId.timeSlotId THEN FALSE 
                    
                    WHEN EXISTS (
                            SELECT 1
                            FROM TimeSlotEntity t2
                            INNER JOIN ScheduleEntity sc2 ON t2.scheduleId = sc2.scheduleId
                            INNER JOIN MatchMakingEntity m2 ON m2.scheduleId = sc2.scheduleId
                            INNER JOIN MatchMakingResourceEntity mr2 ON mr2.id.matchMakingId = m2.matchMakingId
                            WHERE (sc2.date <= CURRENT_DATE AND m2.expiredAt > CURRENT_DATE) AND (t2.begin <= CURRENT_TIME AND t2.end > CURRENT_TIME )
                                    AND mr2.id.stationResourceId = mmr.id.stationResourceId
                            ) THEN FALSE
                    WHEN (bs IS NULL AND (sc.date <= CURRENT_DATE AND t.end < CURRENT_TIME)) THEN FALSE
                    ELSE TRUE 
                END AS allowBooking      
        FROM StationResourceEntity sr
        INNER JOIN AreaEntity a ON a.areaId = sr.areaId
        INNER JOIN StationSpaceEntity ss ON ss.stationSpaceId = a.stationSpaceId
        LEFT JOIN StationEntity s ON ss.stationId = s.stationId
        LEFT JOIN ScheduleEntity sc ON sc.stationId = s.stationId AND sc.scheduleId = :scheduleId AND sc.deleted = FALSE
        LEFT JOIN BookingEntity b ON sc.scheduleId = b.scheduleId AND sc.deleted = FALSE AND b.stationResourceId = sr.stationResourceId
        LEFT JOIN TimeSlotEntity t ON sc.scheduleId = t.scheduleId
        LEFT JOIN BookingSlotEntity bs ON bs.bookingSlotId.timeSlotId = t.timeSlotId
        LEFT JOIN MatchScheduleEntity mc ON mc.id.scheduleId = sc.scheduleId
        LEFT JOIN MatchMakingResourceEntity mmr ON sr.stationResourceId = mmr.id.stationResourceId
        LEFT JOIN MatchMakingSlotEntity ms ON mc.id.matchMakingId = ms.id.matchMakingId AND ms.id.timeSlotId = t.timeSlotId
        WHERE sr.stationResourceId = :stationResourceId
        """)
    List<TimeSlotAllowBookingDao> findAllByScheduleIdAndStationResourceId(Long scheduleId, Long stationResourceId);

    List<TimeSlotEntity> findListByTimeSlotIdIn(List<Long> timeSlotIds);
}
