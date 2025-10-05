package net.pool.station.core.features.schedule.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.domain.schedule.ScheduleCriteria;
import net.pool.station.core.features.schedule.repository.database.ScheduleEntityMapper;
import net.pool.station.core.features.schedule.repository.database.ScheduleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleQueryService {
    ScheduleRepository repository;

    ScheduleEntityMapper mapper;

    protected Optional<Schedule> findById(Long scheduleId) {
        return repository.findByScheduleIdAndDeletedFalse(scheduleId)
                .map(mapper::toDto);
    }

    protected Page<Schedule> findAll(ScheduleCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria.specifications(), pageRequest)
                .map(mapper::toDto);
    }
}
