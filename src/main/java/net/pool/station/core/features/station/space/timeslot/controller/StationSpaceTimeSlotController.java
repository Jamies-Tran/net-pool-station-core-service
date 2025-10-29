package net.pool.station.core.features.station.space.timeslot.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlotUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceTimeSlotController implements StationSpaceTimeSlotApi {
    StationSpaceTimeSlotUseCase stationSpaceTimeSlotUseCase;

    @Override
    public MyValueResponse<?> enable(Long stationSpaceSlotId) {
        stationSpaceTimeSlotUseCase.enable(DomainKey.of(stationSpaceSlotId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long stationSpaceSlotId) {
        stationSpaceTimeSlotUseCase.disable(DomainKey.of(stationSpaceSlotId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long stationSpaceSlotId) {
        stationSpaceTimeSlotUseCase.delete(DomainKey.of(stationSpaceSlotId));

        return MyValueResponse.successNoData();
    }
}
