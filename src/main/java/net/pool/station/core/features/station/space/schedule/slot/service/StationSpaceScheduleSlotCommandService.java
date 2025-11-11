package net.pool.station.core.features.station.space.schedule.slot.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.station.space.schedule.slot.StationSpaceScheduleSlot;
import net.pool.station.core.features.station.space.schedule.slot.repository.database.StationSpaceScheduleSlotEntityMapper;
import net.pool.station.core.features.station.space.schedule.slot.repository.database.StationSpaceScheduleSlotRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceScheduleSlotCommandService {
    StationSpaceScheduleSlotRepository repository;

    StationSpaceScheduleSlotEntityMapper mapper;

    protected void saveAll(Long stationSpaceScheduleId, List<StationSpaceScheduleSlot> slots) {
        List<StationSpaceScheduleSlot> newSlots = slots.stream()
                .map(slot -> slot.withStationSpaceScheduleId(stationSpaceScheduleId))
                .toList();

        repository.saveAll(mapper.toEntity(newSlots));
    }
}
