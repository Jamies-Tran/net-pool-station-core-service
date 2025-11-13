package net.pool.station.core.features.station.space.schedule.schedule.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.station.space.schedule.StationSpaceSchedule;
import net.pool.station.core.features.station.space.schedule.schedule.repository.database.StationSpaceScheduleEntityMapper;
import net.pool.station.core.features.station.space.schedule.schedule.repository.database.StationSpaceScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceScheduleCommandService {
    StationSpaceScheduleRepository repository;

    StationSpaceScheduleEntityMapper mapper;

    protected void saveAll(Long stationSpaceId, List<StationSpaceSchedule> stationSpaceSchedules) {
        List<StationSpaceSchedule> newStationSpaceSchedules = stationSpaceSchedules.stream()
                .map(stationSpaceSchedule -> stationSpaceSchedule
                        .withStationSpaceId(stationSpaceId))
                .toList();

        repository.saveAll(mapper.toEntity(newStationSpaceSchedules));
    }
}
