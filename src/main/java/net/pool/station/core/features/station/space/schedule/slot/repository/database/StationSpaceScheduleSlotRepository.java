package net.pool.station.core.features.station.space.schedule.slot.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationSpaceScheduleSlotRepository extends JpaRepository<StationSpaceScheduleSlotEntity, Long> {
    @Query("""
        SELECT sss.stationSpaceScheduleId
        FROM StationSpaceScheduleEntity sss
        WHERE sss.stationSpaceId = :stationSpaceId AND sss.scheduleId = :scheduleId
        """)
    Optional<Long> findStationSpaceScheduleId(Long stationSpaceId, Long scheduleId);

    @Query("""
        SELECT ssss
        FROM StationSpaceScheduleSlotEntity ssss
        INNER JOIN StationSpaceScheduleEntity sss ON sss.stationSpaceScheduleId = ssss.stationSpaceScheduleId
        INNER JOIN StationSpaceEntity ss ON sss.stationSpaceId = ss.stationSpaceId
        WHERE ss.stationSpaceId = :stationSpaceId AND ssss.timeSlotId = :timeSlotId
        """)
    Optional<StationSpaceScheduleSlotEntity> findByStationSpaceIdAndTimeSlotId(Long stationSpaceId, Long timeSlotId);

    @Query("""
        SELECT ssss
        FROM StationSpaceScheduleSlotEntity ssss
        INNER JOIN StationSpaceScheduleEntity sss ON sss.stationSpaceScheduleId = ssss.stationSpaceScheduleId
        INNER JOIN StationSpaceEntity ss ON sss.stationSpaceId = ss.stationSpaceId
        WHERE ss.stationSpaceId = :stationSpaceId AND sss.scheduleId = :scheduleId
        """)
    List<StationSpaceScheduleSlotEntity> findAllByStationSpaceIdAndScheduleId(Long stationSpaceId, Long scheduleId);
}
