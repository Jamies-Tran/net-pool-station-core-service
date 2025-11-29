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
                COALESCE(t1.timeSlotId, t2.timeSlotId) AS timeSlotId,
                CASE 
                    WHEN bs IS NOT NULL AND COALESCE(t1.timeSlotId, t2.timeSlotId) = bs.bookingSlotId.timeSlotId THEN FALSE 
                    WHEN bs IS NULL AND COALESCE(t1.end, t2.end) < CURRENT_TIME THEN FALSE
                    ELSE TRUE 
                END AS allowBooking      
        FROM StationResourceEntity sr
        INNER JOIN AreaEntity a ON a.areaId = sr.areaId
        INNER JOIN StationSpaceEntity ss ON ss.stationSpaceId = a.stationSpaceId
        LEFT JOIN StationSpaceScheduleEntity sss ON sss.stationSpaceId = ss.stationSpaceId AND sss.scheduleId = :scheduleId
        LEFT JOIN StationSpaceScheduleSlotEntity ssst ON ssst.stationSpaceScheduleId = sss.stationSpaceScheduleId
        LEFT JOIN TimeSlotEntity t1 ON ssst.timeSlotId = t1.timeSlotId
        LEFT JOIN StationEntity s ON ss.stationId = s.stationId
        LEFT JOIN ScheduleEntity sc ON sc.stationId = s.stationId AND sc.scheduleId = :scheduleId
        LEFT JOIN TimeSlotEntity t2 ON sc.scheduleId = t2.scheduleId
        LEFT JOIN BookingSlotEntity bs ON bs.bookingSlotId.timeSlotId = COALESCE(t1.timeSlotId, t2.timeSlotId)
        WHERE sr.stationResourceId = :stationResourceId
        """)
    List<TimeSlotAllowBookingDao> findAllByScheduleIdAndStationResourceId(Long scheduleId, Long stationResourceId);

//    @Query("""
//        SELECT
//                ts AS timeSlot,
//                CASE
//                    WHEN bs IS NOT NULL AND ts.timeSlotId = bs.bookingSlotId.timeSlotId THEN FALSE
//                    WHEN bs IS NULL AND ts.end < CURRENT_TIME THEN FALSE
//                    ELSE TRUE
//                END AS allowBooking
//        FROM BookingSlotEntity bs
//        LEFT JOIN TimeSlotEntity ts ON ts.timeSlotId = bs.bookingSlotId.timeSlotId
//        WHERE ts.timeSlotId IN :timeSlotIds
//        """)
//    List<TimeSlotAllowBookingDao> findAllByTimeSlotIdIn(List<Long> timeSlotIds);

    List<TimeSlotEntity> findListByTimeSlotIdIn(List<Long> timeSlotIds);
}
