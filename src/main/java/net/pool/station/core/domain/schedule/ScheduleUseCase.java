package net.pool.station.core.domain.schedule;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface ScheduleUseCase {
    void save(Schedule schedule);

    void save(List<Schedule> schedules, Schedule.TimeSlotConfig timeSlotConfig);

    Optional<Schedule> findById(DomainKey<Long> scheduleId);

    Page<Schedule> findAll(ScheduleCriteria criteria, PageRequest pageRequest);

    void update(DomainKey<Long> scheduleId, Schedule schedule);

    void enable(DomainKey<Long> scheduleId);

    void disable(DomainKey<Long> scheduleId);

    void delete(DomainKey<Long> scheduleId);
}
