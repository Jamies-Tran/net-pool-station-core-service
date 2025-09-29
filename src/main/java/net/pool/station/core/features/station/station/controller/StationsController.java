package net.pool.station.core.features.station.station.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.station.StationUseCase;
import net.pool.station.core.features.station.station.controller.models.StationRequest;
import net.pool.station.core.features.station.station.controller.models.StationRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE, makeFinal = true)
public class StationsController implements StationsApi {
    StationUseCase stationUseCase;

    StationRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> save(StationRequest request) {
        stationUseCase.save(requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }
}
