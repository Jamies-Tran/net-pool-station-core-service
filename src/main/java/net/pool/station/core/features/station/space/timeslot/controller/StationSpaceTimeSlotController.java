package net.pool.station.core.features.station.space.timeslot.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlotId;
import net.pool.station.core.domain.station.space.timeslot.StationSpaceTimeSlotUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceTimeSlotController implements StationSpaceTimeSlotApi {
    StationSpaceTimeSlotUseCase stationSpaceTimeSlotUseCase;

    @Override
    public MyValueResponse<?> enable(Long stationId, Long spaceId, Long timeSlotId) {
        StationSpaceTimeSlotId id = StationSpaceTimeSlotId
                .of(timeSlotId, stationId, spaceId);
        stationSpaceTimeSlotUseCase.enable(DomainKey.of(id));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long stationId, Long spaceId, Long timeSlotId) {
        StationSpaceTimeSlotId id = StationSpaceTimeSlotId
                .of(timeSlotId, stationId, spaceId);
        stationSpaceTimeSlotUseCase.disable(DomainKey.of(id));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long stationId, Long spaceId, Long timeSlotId) {
        StationSpaceTimeSlotId id = StationSpaceTimeSlotId
                .of(timeSlotId, stationId, spaceId);
        stationSpaceTimeSlotUseCase.delete(DomainKey.of(id));

        return MyValueResponse.successNoData();
    }
}
