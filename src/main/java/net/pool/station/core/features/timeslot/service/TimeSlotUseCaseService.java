package net.pool.station.core.features.timeslot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.ETimeSlotStatus;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.timeslot.TimeSlot;
import net.pool.station.core.domain.timeslot.TimeSlotCriteria;
import net.pool.station.core.domain.timeslot.TimeSlotUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeSlotUseCaseService implements TimeSlotUseCase {
    TimeSlotCommandService commandService;

    TimeSlotQueryService queryService;

    @Override
    @Transactional
    @Async("timeSlotExecutor")
    public void save(List<TimeSlot> timeSlots) {
        commandService.save(timeSlots);
    }

    @Override
    @Transactional
    public void save(DomainKey<Long> scheduleId, List<TimeSlot> timeSlots) {
        commandService.save(scheduleId.value(), timeSlots);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TimeSlot> findById(DomainKey<Long> timeSlotId) {
        return queryService.findById(timeSlotId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeSlot> findAllByIdIn(List<Long> timeSlotIds) {
        return queryService.findAllByTimeSlotIdIn(timeSlotIds);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TimeSlot> findAll(TimeSlotCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeSlot> findAllByScheduleId(DomainKey<Long> scheduleId) {
        return queryService.findAllByScheduleId(scheduleId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TimeSlot> findAllByScheduleIdAndStationResourceId(DomainKey<Long> scheduleId, DomainKey<Long> stationResourceId) {
        return queryService.findAllByScheduleIdAndStationResourceId(scheduleId.value(), stationResourceId.value());
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> timeSlotId) {
        commandService.delete(timeSlotId.value());
    }


}
