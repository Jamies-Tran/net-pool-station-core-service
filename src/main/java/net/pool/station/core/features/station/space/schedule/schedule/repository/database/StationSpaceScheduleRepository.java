package net.pool.station.core.features.station.space.schedule.schedule.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StationSpaceScheduleRepository extends JpaRepository<StationSpaceScheduleEntity, Long> {
    List<StationSpaceScheduleEntity> findAllByStationSpaceId(Long stationSpaceId);
}
