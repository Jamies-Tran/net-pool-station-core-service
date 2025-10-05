package net.pool.station.core.features.timeslot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.ETimeSlotStatus;
import net.pool.station.core.domain.timeslot.TimeSlot;
import net.pool.station.core.features.timeslot.repository.database.TimeSlotEntity;
import net.pool.station.core.features.timeslot.repository.database.TimeSlotEntityMapper;
import net.pool.station.core.features.timeslot.repository.database.TimeSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TimeSlotCommandService {
    TimeSlotRepository repository;

    TimeSlotEntityMapper mapper;

    protected void save(List<TimeSlot> timeSlots) {
        repository.saveAll(mapper.toEntity(timeSlots));
    }

    protected void save(Long scheduleId, List<TimeSlot> timeSlots) {
        repository.deleteAllByScheduleId(scheduleId);

        repository.saveAll(mapper.toEntity(timeSlots));
    }

    protected void updateStatus(Long timeSlotId, ETimeSlotStatus status) {
        repository.findById(timeSlotId)
                .ifPresentOrElse(
                        foundTimeSlot -> {
                            foundTimeSlot.setStatusCode(status.getCode());
                            foundTimeSlot.setStatusName(status.getName());
                            repository.save(foundTimeSlot);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }


    protected void delete(Long timeSlotId) {
        repository.deleteById(timeSlotId);
    }
}
