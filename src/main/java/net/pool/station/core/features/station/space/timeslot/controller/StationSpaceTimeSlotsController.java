package net.pool.station.core.features.station.space.timeslot.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlotUseCase;
import net.pool.station.core.features.station.space.timeslot.controller.models.StationSpaceTimeSlotListRequest;
import net.pool.station.core.features.station.space.timeslot.controller.models.StationSpaceTimeSlotRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceTimeSlotsController implements StationSpaceTimeSlotsApi {
    StationSpaceTimeSlotUseCase stationSpaceTimeSlotUseCase;

    StationSpaceTimeSlotRequestMapper requestMapper;


    @Override
    public MyValueResponse<?> save(StationSpaceTimeSlotListRequest request) {
        stationSpaceTimeSlotUseCase.save(requestMapper
                .toDto(request.stationSpaceTimeSlots()));

        return MyValueResponse.successNoData();
    }
}
