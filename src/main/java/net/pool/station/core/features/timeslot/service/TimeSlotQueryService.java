package net.pool.station.core.features.timeslot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.bootstrap.enums.ETimeSlotStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.timeslot.TimeSlot;
import net.pool.station.core.domain.timeslot.TimeSlotCriteria;
import net.pool.station.core.features.timeslot.repository.database.TimeSlotEntityMapper;
import net.pool.station.core.features.timeslot.repository.database.TimeSlotRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeSlotQueryService {
    TimeSlotRepository repository;

    TimeSlotEntityMapper mapper;

    protected Optional<TimeSlot> findById(Long timeSlotId) {
        return repository.findById(timeSlotId)
                .map(mapper::toDto);
    }

    protected Page<TimeSlot> findAll(TimeSlotCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }

    protected List<TimeSlot> findAllByScheduleId(Long scheduleId) {
        return mapper.toDto(repository.findAllByScheduleId(scheduleId));
    }

    protected List<TimeSlot> findAllByScheduleIdAndStationResourceId(Long scheduleId, Long stationResourceId) {
        List<Long> timeSlotIds = repository.findAllByScheduleIdAndStationResourceId(scheduleId, stationResourceId);

        return mapper.toDto(repository.findAllByTimeSlotIdIn(timeSlotIds));
    }

    protected List<TimeSlot> findAllByTimeSlotIdIn(List<Long> timeSlotIds) {
        return mapper.toDto(repository.findAllByTimeSlotIdIn(timeSlotIds));
    }
}
