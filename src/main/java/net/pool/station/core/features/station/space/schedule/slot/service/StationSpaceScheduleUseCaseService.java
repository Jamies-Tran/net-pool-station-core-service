package net.pool.station.core.features.station.space.schedule.slot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.space.schedule.slot.StationSpaceScheduleSlot;
import net.pool.station.core.domain.station.space.schedule.slot.StationSpaceScheduleSlotUseCase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceScheduleUseCaseService implements StationSpaceScheduleSlotUseCase {
    StationSpaceScheduleSlotCommandService commandService;

    @Override
    public void save(DomainKey<Long> stationSpaceScheduleId, List<StationSpaceScheduleSlot> slots) {
        commandService.saveAll(stationSpaceScheduleId.value(), slots);
    }
}
