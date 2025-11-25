package net.pool.station.core.domain.timeslot;

import net.pool.station.core.bootstrap.enums.ETimeSlotStatus;
import net.pool.station.core.domain.DomainKey;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

public interface TimeSlotUseCase {
    void save(List<TimeSlot> timeSlots);

    void save(DomainKey<Long> scheduleId, List<TimeSlot> timeSlots);

    Optional<TimeSlot> findById(DomainKey<Long> timeSlotId);

    List<TimeSlot> findAllByIdIn(List<Long> timeSlotIds);

    Page<TimeSlot> findAll(TimeSlotCriteria criteria, PageRequest pageRequest);

    List<TimeSlot> findAllByScheduleId(DomainKey<Long> scheduleId);

    List<TimeSlot> findAllByScheduleIdAndStationResourceId(DomainKey<Long> scheduleId, DomainKey<Long> stationResourceId);

    void delete(DomainKey<Long> timeSlotId);

}
