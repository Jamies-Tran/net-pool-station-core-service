package net.pool.station.core.features.schedule.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EScheduleStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.features.schedule.repository.database.ScheduleEntity;
import net.pool.station.core.features.schedule.repository.database.ScheduleEntityMapper;
import net.pool.station.core.features.schedule.repository.database.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleCommandService {
    ScheduleRepository repository;

    ScheduleEntityMapper mapper;

    protected Long save(Schedule schedule) {
        validate(schedule);
        return repository.save(mapper.toEntity(schedule))
                .getScheduleId();
    }

    protected List<Long> save(List<Schedule> schedules) {
        validate(schedules);
        return repository.saveAll(mapper.toEntity(schedules))
                .stream()
                .map(ScheduleEntity::getScheduleId)
                .toList();
    }

    protected void update(Long scheduleId, Schedule schedule) {
        Optional<ScheduleEntity> existSchedule = repository.findByScheduleIdAndDeletedFalse(scheduleId);
        if (existSchedule.isEmpty()) {
            throw new MyResourceNotFoundException();
        }
        ScheduleEntity foundSchedule = existSchedule.get();
        validate(schedule, foundSchedule);
        mapper.update(foundSchedule, schedule);
        repository.save(foundSchedule);
    }

    protected void updateStatus(Long scheduleId, EScheduleStatus status) {
        repository.findByScheduleIdAndDeletedFalse(scheduleId)
                .ifPresentOrElse(
                        foundSchedule -> {
                            foundSchedule.setStatusCode(status.getCode());
                            foundSchedule.setStatusName(status.getName());
                            repository.save(foundSchedule);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void delete(Long scheduleId) {
        repository.findByScheduleIdAndDeletedFalse(scheduleId)
                .ifPresent(foundSchedule -> {
                            if (MyObjectUtils.isNotEquals(foundSchedule.getStatusCode(),
                                    EScheduleStatus.DRAFT.getCode())) {
                                throw new MyResourceNotValid("Lịch hoạt động không thể xóa");
                            }
                            foundSchedule.setDeleted(true);
                            repository.save(foundSchedule);
                        });
    }

    private void validate(Schedule schedule, ScheduleEntity exists) {
        if (repository.existsInBookingOrMatchMaking(exists.getScheduleId())) {
            throw new MyResourceNotValid("Lịch hoạt động không thể cập nhật.");
        }

        if (MyObjectUtils.isNotEquals(schedule.date(), exists.getDate())) {
            validate(schedule);
        }
    }

    private void validate(Schedule schedule) {
        if (repository.existsByDateAndStationId(schedule.date(), schedule.stationId())) {
            throw new MyResourceDuplicateException("Lịch hoạt động không được trùng lặp");
        }
    }

    private void validate(List<Schedule> schedules) {
        schedules.forEach(this::validate);
        schedules.stream()
                .collect(Collectors.groupingBy(Schedule::stationId))
                .forEach((key, value) -> {
                    List<LocalDate> dates = value.stream()
                            .map(Schedule::date)
                            .toList();
                    Set<LocalDate> set = new HashSet<>(dates);
                    if (set.size() < dates.size()) {
                        throw new MyResourceDuplicateException("Lịch hoạt động không được trùng lặp");
                    }
                });

    }
}
