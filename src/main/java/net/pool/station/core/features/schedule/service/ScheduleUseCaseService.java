package net.pool.station.core.features.schedule.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EScheduleStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.account.AccountUseCase;
import net.pool.station.core.domain.email.sending.EmailUseCase;
import net.pool.station.core.domain.schedule.Schedule;
import net.pool.station.core.domain.schedule.ScheduleCriteria;
import net.pool.station.core.domain.schedule.ScheduleUseCase;
import net.pool.station.core.domain.timeslot.TimeSlot;
import net.pool.station.core.domain.timeslot.TimeSlotCriteria;
import net.pool.station.core.domain.timeslot.TimeSlotUseCase;
import net.pool.station.core.features.email.sending.EmailUseCaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ScheduleUseCaseService implements ScheduleUseCase {
    ScheduleCommandService commandService;

    ScheduleQueryService queryService;

    TimeSlotUseCase timeSlotUseCase;

    @Override
    @Transactional
    public void save(Schedule schedule) {
        Long scheduleId = commandService.save(schedule);

        timeSlotUseCase.save(TimeSlot.from(scheduleId, schedule.timeSlotConfig()));
    }

    @Override
    @Transactional
    public void save(List<Schedule> schedules, Schedule.TimeSlotConfig timeSlotConfig) {
        List<Long> scheduleIds = commandService.save(schedules);

        timeSlotUseCase.save(TimeSlot.from(scheduleIds, timeSlotConfig));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Schedule> findById(DomainKey<Long> scheduleId) {
        List<TimeSlot> timeSlots = timeSlotUseCase.findAllByScheduleId(scheduleId);

        return queryService.findById(scheduleId.value())
                .map(schedule -> schedule.withTimeSlots(timeSlots));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Schedule> findById(DomainKey<Long> scheduleId, DomainKey<Long> stationResourceId) {
        List<TimeSlot> timeSlots = timeSlotUseCase
                .findAllByScheduleIdAndStationResourceId(scheduleId, stationResourceId);

        return queryService.findById(scheduleId.value())
                .map(schedule -> schedule.withTimeSlots(timeSlots));
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Schedule> findAllByStationResource(ScheduleCriteria criteria, PageRequest pageRequest) {
        return queryService.findAllByStationResource(criteria, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Schedule> findAllByStation(ScheduleCriteria criteria, PageRequest pageRequest) {
        return queryService.findAllByStation(criteria, pageRequest);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> scheduleId, Schedule schedule) {
        commandService.update(scheduleId.value(), schedule);

        timeSlotUseCase.save(scheduleId, TimeSlot.from(scheduleId.value(), schedule.timeSlotConfig()));
    }

    @Override
    @Transactional
    public void enable(DomainKey<Long> scheduleId) {
        commandService.updateStatus(scheduleId.value(), EScheduleStatus.ENABLED);
    }

    @Override
    @Transactional
    public void disable(DomainKey<Long> scheduleId) {
        commandService.updateStatus(scheduleId.value(), EScheduleStatus.DISABLED);
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> scheduleId) {
        commandService.delete(scheduleId.value());
    }
}
