package net.pool.station.core.features.station.space.timeslot.repository.database;

import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlotCriteria;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlotId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationSpaceTimeSlotRepository extends JpaRepository<StationSpaceTimeSlotEntity, Long> {
    @Query("""
        SELECT sst
        FROM StationSpaceTimeSlotEntity sst
        WHERE sst.stationSpaceTimeSlotId.stationId = :#{#id.stationId()}
            AND sst.stationSpaceTimeSlotId.spaceId = :#{#id.spaceId()}
            AND sst.stationSpaceTimeSlotId.timeSlotId = :#{#id.timeSlotId()}
        """)
    Optional<StationSpaceTimeSlotEntity> findById(StationSpaceTimeSlotId id);

    @Query("""
        SELECT sst
        FROM StationSpaceTimeSlotEntity sst
        INNER JOIN TimeSlotEntity ts ON sst.stationSpaceTimeSlotId.timeSlotId = ts.timeSlotId
        INNER JOIN ScheduleEntity s ON ts.scheduleId = s.scheduleId
        WHERE (:#{#criteria.scheduleId()} = 0
                    OR s.scheduleId = :#{#criteria.scheduleId()})
             AND (:#{#criteria.stationId()} = 0
                     OR sst.stationSpaceTimeSlotId.stationId = :#{#criteria.stationId()})
             AND (:#{#criteria.spaceId()} = 0
                     OR sst.stationSpaceTimeSlotId.spaceId = :#{#criteria.spaceId()})
             AND (:#{#criteria.statusCodes().empty} = TRUE
                     OR sst.statusCode IN :#{#criteria.statusCodes()})
        """)
    Page<StationSpaceTimeSlotEntity> findAll(StationSpaceTimeSlotCriteria criteria, Pageable pageable);

}
