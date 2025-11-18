package net.pool.station.core.features.station.space.schedule.schedule.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.space.schedule.StationSpaceSchedule;
import net.pool.station.core.domain.station.space.schedule.StationSpaceScheduleUseCase;
import net.pool.station.core.features.station.space.schedule.schedule.controller.models.StationSpaceScheduleListRequest;
import net.pool.station.core.features.station.space.schedule.schedule.controller.models.StationSpaceScheduleRequestMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceSchedulesController implements StationSpaceSchedulesApi {
    StationSpaceScheduleUseCase stationSpaceScheduleUseCase;

    StationSpaceScheduleRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> save(Long stationSpaceId, StationSpaceScheduleListRequest request) {
        List<StationSpaceSchedule> stationSpaceSchedules = requestMapper
                .toDto(request.stationSpaceSchedules());
        stationSpaceScheduleUseCase.save(DomainKey.of(stationSpaceId), stationSpaceSchedules);

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> deleteAllByStationSpaceId(Long stationSpaceId) {
        stationSpaceScheduleUseCase.deleteAllByStationSpaceId(DomainKey.of(stationSpaceId));

        return MyValueResponse.successNoData();
    }
}
