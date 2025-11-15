package net.pool.station.core.features.timeslot.repository.database;

import net.pool.station.core.domain.timeslot.TimeSlotCriteria;
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
        SELECT DISTINCT COALESCE(t1.timeSlotId, t2.timeSlotId) AS timeSlotId
        FROM StationResourceEntity sr
        INNER JOIN AreaEntity a ON a.areaId = sr.areaId
        INNER JOIN StationSpaceEntity ss ON ss.stationSpaceId = a.stationSpaceId
        LEFT JOIN StationSpaceScheduleEntity sss ON sss.stationSpaceId = ss.stationSpaceId
        LEFT JOIN StationSpaceScheduleSlotEntity ssst ON ssst.stationSpaceScheduleId = sss.stationSpaceScheduleId
        LEFT JOIN TimeSlotEntity t1 ON ssst.timeSlotId = t1.timeSlotId
        LEFT JOIN StationEntity s ON ss.stationId = s.stationId
        LEFT JOIN ScheduleEntity sc ON sc.stationId = s.stationId AND sc.scheduleId = :scheduleId
        LEFT JOIN TimeSlotEntity t2 ON sc.scheduleId = t2.scheduleId
        WHERE sss.scheduleId = :scheduleId
                AND sr.stationResourceId = :stationResourceId
        """)
    List<Long> findAllByScheduleIdAndStationResourceId(Long scheduleId, Long stationResourceId);

    List<TimeSlotEntity> findAllByTimeSlotIdIn(List<Long> timeSlotIds);
}
