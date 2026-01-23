package net.pool.station.core.features.schedule.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EScheduleStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.domain.schedule.ScheduleCriteria;
import net.pool.station.core.features.schedule.repository.database.ScheduleEntity;
import net.pool.station.core.features.schedule.repository.database.ScheduleEntityMapper;
import net.pool.station.core.features.schedule.repository.database.ScheduleRepository;
import net.pool.station.core.features.schedule.repository.database.models.ScheduleDao;
import net.pool.station.core.features.schedule.repository.database.models.ScheduleDaoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleQueryService {
    ScheduleRepository repository;

    ScheduleEntityMapper mapper;

    ScheduleDaoMapper daoMapper;

    protected Optional<Schedule> findById(Long scheduleId) {
        return repository.findByScheduleIdAndDeletedFalse(scheduleId)
                .map(s -> {
                    Map<Long, Boolean> allowUpdateMap = repository
                            .findAllWithAllowUpdateIn(List.of(s.getScheduleId()))
                            .stream()
                            .collect(Collectors.toMap(ScheduleDao::getScheduleId, ScheduleDao::getAllowUpdate));

                    return mapper.toDto(s).withAllowUpdate(allowUpdateMap
                            .computeIfAbsent(s.getScheduleId(), k -> true));
                });
    }

    protected Page<Schedule> findAllByStation(ScheduleCriteria criteria, PageRequest pageRequest) {
        Page<ScheduleEntity> entity = repository.findAllByStation(criteria, pageRequest);
        List<Long> scheduleIds = entity.stream()
                .map(ScheduleEntity::getScheduleId)
                .toList();
        Map<Long, Boolean> allowUpdateMap = repository.findAllWithAllowUpdateIn(scheduleIds)
                .stream()
                .collect(Collectors.toMap(ScheduleDao::getScheduleId, ScheduleDao::getAllowUpdate));

        return entity.map(e -> mapper.toDto(e)
                .withAllowUpdate(allowUpdateMap.computeIfAbsent(e.getScheduleId(), k -> true)));
    }

    protected Page<Schedule> findAllByStationResource(ScheduleCriteria criteria, PageRequest pageRequest) {
        Page<Long> scheduleIds = repository.findAllByStationResource(criteria, pageRequest);
        List<ScheduleEntity> foundSchedules = repository.findAllByScheduleIdIn(scheduleIds.getContent(),
                pageRequest.getSort());
        Map<Long, Boolean> allowUpdateMap = repository.findAllWithAllowUpdateIn(scheduleIds.getContent())
                .stream()
                .collect(Collectors.toMap(ScheduleDao::getScheduleId, ScheduleDao::getAllowUpdate));
        List<Schedule> schedules = foundSchedules.stream()
                .map(s -> mapper.toDto(s)
                        .withAllowUpdate(allowUpdateMap.computeIfAbsent(s.getScheduleId(), k -> true)))
                .toList();
        return new PageImpl<>(schedules, pageRequest, scheduleIds.getTotalElements());
    }

    protected Page<Schedule> findAllByStationSpace(ScheduleCriteria criteria, PageRequest pageRequest) {
        Page<Long> scheduleIds = repository.findAllByStationSpace(criteria, pageRequest);
        List<ScheduleEntity> foundSchedules = repository.findAllByScheduleIdIn(scheduleIds.getContent(),
                pageRequest.getSort());
        Map<Long, Boolean> allowUpdateMap = repository.findAllWithAllowUpdateIn(scheduleIds.getContent())
                .stream()
                .collect(Collectors.toMap(ScheduleDao::getScheduleId, ScheduleDao::getAllowUpdate));
        List<Schedule> schedules = foundSchedules.stream()
                .map(s -> mapper.toDto(s).withAllowUpdate(allowUpdateMap
                                .computeIfAbsent(s.getScheduleId(), k -> true)))
                .toList();

        return new PageImpl<>(schedules, pageRequest, scheduleIds.getTotalElements());
    }

    protected List<Schedule> findAllByStationIdAndDateFromAndDateCount(
            Long stationId, LocalDate dateFrom, Integer dateCount) {
        List<LocalDate> dateList = dateFrom.datesUntil(dateFrom.plusDays(dateCount)).toList();
        List<ScheduleEntity> schedules = repository
                .findAllByStationIdAndDateGreaterThanEqualAndStatusCodeAndDeletedFalse(stationId, dateFrom, EScheduleStatus.ENABLED.getCode());
        List<ScheduleEntity> newSchedules = new ArrayList<>();
        if (!CollectionUtils.isEmpty(schedules)) {
            LocalDate lastDate = dateList
                    .stream()
                    .max(Comparator.comparing(d -> d))
                    .get();
            for (LocalDate date : dateList) {
                Map<LocalDate, ScheduleEntity> scheduleMap = schedules
                        .stream()
                        .collect(Collectors.toMap(ScheduleEntity::getDate, Function.identity()));
                Queue<ScheduleEntity> scheduleQueue = new LinkedList<>(schedules);
                ScheduleEntity schedule = scheduleMap.computeIfAbsent(date, k -> null);
                if (schedule == null) {
                    ScheduleEntity nextSchedule = scheduleMap.computeIfAbsent(lastDate, k -> null);
                    while (scheduleQueue.poll() != null && nextSchedule == null) {
                        lastDate = lastDate.plusDays(1);
                        nextSchedule = scheduleMap.computeIfAbsent(lastDate, k -> null);
                    }

                    if (!MyObjectUtils.isEmpty(nextSchedule)) {
                        newSchedules.add(nextSchedule);
                    }
                    lastDate = lastDate.plusDays(1);
                } else {
                    newSchedules.add(schedule);
                }
            }
        }

        return mapper.toDto(newSchedules);
    }
}
