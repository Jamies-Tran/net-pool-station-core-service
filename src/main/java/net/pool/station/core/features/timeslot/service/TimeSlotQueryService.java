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
import net.pool.station.core.features.timeslot.repository.database.dao.TimeSlotAllowBookingDao;
import net.pool.station.core.features.timeslot.repository.database.dao.TimeSlotDaoMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeSlotQueryService {
    TimeSlotRepository repository;

    TimeSlotEntityMapper mapper;

    TimeSlotDaoMapper daoMapper;

    protected Optional<TimeSlot> findById(Long timeSlotId) {
        return repository.findById(timeSlotId)
                .map(mapper::toDto);
    }

    protected Page<TimeSlot> findAll(TimeSlotCriteria criteria, PageRequest pageRequest) {
        return repository.findAll(criteria, pageRequest)
                .map(mapper::toDto);
    }

    protected List<TimeSlot> findAllByScheduleId(Long scheduleId) {
        return daoMapper.toDto(repository.findAllByScheduleId(scheduleId));
    }

    protected List<TimeSlot> findAllByScheduleIdAndStationResourceId(Long scheduleId, Long stationResourceId) {
        Map<Long, List<Boolean>> timeSlotId = repository
                .findAllByScheduleIdAndStationResourceId(scheduleId, stationResourceId).stream()
                .collect(Collectors.groupingBy(TimeSlotAllowBookingDao::getTimeSlotId, Collectors
                        .mapping(TimeSlotAllowBookingDao::getAllowBooking, Collectors.toList())));

        return repository.findAllById(timeSlotId.keySet())
                .stream()
                .map(dao -> mapper
                        .toDto(dao).withAllowBooking(timeSlotId.computeIfAbsent(dao.getTimeSlotId(), a -> List.of())
                                .stream()
                                .allMatch(c -> c)))
                .toList();
    }

    protected List<TimeSlot> findAllByTimeSlotIdIn(List<Long> timeSlotIds) {
        return mapper.toDto(repository.findListByTimeSlotIdIn(timeSlotIds));
    }
}
