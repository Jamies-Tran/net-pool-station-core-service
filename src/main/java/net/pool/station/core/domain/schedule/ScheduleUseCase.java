package net.pool.station.core.domain.schedule;

import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ScheduleUseCase {
    void save(Schedule schedule);

    void save(List<Schedule> schedules, Schedule.TimeSlotConfig timeSlotConfig);

    Optional<Schedule> findById(DomainKey<Long> scheduleId);

    Optional<Schedule> findById(DomainKey<Long> scheduleId, DomainKey<Long> stationResourceId);

    Page<Schedule> findAllByStation(ScheduleCriteria criteria, PageRequest pageRequest);

    Page<Schedule> findAllByStationResource(ScheduleCriteria criteria, PageRequest pageRequest);

    Page<Schedule> findAllByStationSpace(ScheduleCriteria criteria, PageRequest pageRequest);

    List<Schedule> findAllByDateFromAndDateCount(LocalDate dateFrom, Integer dateCount);

    void update(DomainKey<Long> scheduleId, Schedule schedule);

    void enable(DomainKey<Long> scheduleId);

    void disable(DomainKey<Long> scheduleId);

    void delete(DomainKey<Long> scheduleId);
}
