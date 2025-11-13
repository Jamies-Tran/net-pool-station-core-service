package net.pool.station.core.features.schedule.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.domain.schedule.ScheduleCriteria;
import net.pool.station.core.features.schedule.repository.database.ScheduleEntity;
import net.pool.station.core.features.schedule.repository.database.ScheduleEntityMapper;
import net.pool.station.core.features.schedule.repository.database.ScheduleRepository;
import net.pool.station.core.features.schedule.repository.database.models.ScheduleDaoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleQueryService {
    ScheduleRepository repository;

    ScheduleEntityMapper mapper;

    ScheduleDaoMapper daoMapper;

    protected Optional<Schedule> findById(Long scheduleId) {
        return repository.findByScheduleIdAndDeletedFalse(scheduleId)
                .map(mapper::toDto);
    }

    protected Page<Schedule> findAllByStation(ScheduleCriteria criteria, PageRequest pageRequest) {
        return repository.findAllByStation(criteria, pageRequest)
                .map(mapper::toDto);
    }

    protected Page<Schedule> findAllByStationResource(ScheduleCriteria criteria, PageRequest pageRequest) {
        Page<Long> scheduleIds = repository.findAllByStationResource(criteria, pageRequest);
        List<ScheduleEntity> schedules = repository.findAllByScheduleIdIn(scheduleIds.getContent());

        return new PageImpl<>(mapper.toDto(schedules), pageRequest, scheduleIds.getTotalElements());
    }
}
