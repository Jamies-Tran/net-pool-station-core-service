package net.pool.station.core.features.station.space.schedule.schedule.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationSpaceScheduleRepository extends JpaRepository<StationSpaceScheduleEntity, Long> {
}
