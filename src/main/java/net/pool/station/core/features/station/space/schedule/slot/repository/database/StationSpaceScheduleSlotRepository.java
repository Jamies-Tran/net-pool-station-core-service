package net.pool.station.core.features.station.space.schedule.slot.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationSpaceScheduleSlotRepository extends JpaRepository<StationSpaceScheduleSlotEntity, Long> {
    @Query("""
        SELECT sss.stationSpaceScheduleId
        FROM StationSpaceScheduleEntity sss
        WHERE sss.stationSpaceId = :stationSpaceId AND sss.scheduleId = :scheduleId
        """)
    Optional<Long> findStationSpaceScheduleId(Long stationSpaceId, Long scheduleId);
}
