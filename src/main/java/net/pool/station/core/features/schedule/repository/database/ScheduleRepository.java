package net.pool.station.core.features.schedule.repository.database;

import net.pool.station.core.domain.schedule.ScheduleCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long>, JpaSpecificationExecutor<ScheduleEntity> {
    Boolean existsByDateAndStationId(LocalDate date, Long stationId);

    Optional<ScheduleEntity> findByScheduleIdAndDeletedFalse(Long scheduleId);
}
