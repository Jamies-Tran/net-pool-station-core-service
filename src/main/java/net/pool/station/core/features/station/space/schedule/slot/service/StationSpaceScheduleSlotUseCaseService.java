package net.pool.station.core.features.station.space.schedule.slot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.space.schedule.slot.StationSpaceScheduleSlot;
import net.pool.station.core.domain.station.space.schedule.slot.StationSpaceScheduleSlotUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceScheduleSlotUseCaseService implements StationSpaceScheduleSlotUseCase {
    StationSpaceScheduleSlotCommandService commandService;

    @Override
    @Transactional
    public void save(DomainKey<Long> stationSpaceId,
                     DomainKey<Long> scheduleId,
                     List<StationSpaceScheduleSlot> slots) {
        commandService.saveAll(stationSpaceId.value(), scheduleId.value(), slots);
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> stationSpaceId, DomainKey<Long> timeSlotId) {
        commandService.delete(stationSpaceId.value(), timeSlotId.value());
    }

    @Override
    @Transactional
    public void deleteAll(DomainKey<Long> stationSpaceId, DomainKey<Long> scheduleId) {
        commandService.deleteAll(stationSpaceId.value(), scheduleId.value());
    }
}
