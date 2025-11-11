package net.pool.station.core.features.station.space.schedule.slot.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationSpaceScheduleSlotRepository extends JpaRepository<StationSpaceScheduleSlotEntity, Long> {
}
