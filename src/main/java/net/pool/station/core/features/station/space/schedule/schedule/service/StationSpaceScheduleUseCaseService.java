package net.pool.station.core.features.station.space.schedule.schedule.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.space.schedule.StationSpaceSchedule;
import net.pool.station.core.domain.station.space.schedule.StationSpaceScheduleUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceScheduleUseCaseService implements StationSpaceScheduleUseCase {
    StationSpaceScheduleCommandService commandService;

    @Override
    @Transactional
    public void save(DomainKey<Long> stationSpaceId, List<StationSpaceSchedule> stationSpaceSchedules) {
        commandService.saveAll(stationSpaceId.value(), stationSpaceSchedules);
    }
}
