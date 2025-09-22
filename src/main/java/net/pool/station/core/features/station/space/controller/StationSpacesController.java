package net.pool.station.core.features.station.space.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.station.space.StationSpaceUseCase;
import net.pool.station.core.features.station.space.controller.models.StationSpaceRequest;
import net.pool.station.core.features.station.space.controller.models.StationSpaceRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpacesController implements StationSpacesApi {
    StationSpaceUseCase stationSpaceUseCase;

    StationSpaceRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> save(StationSpaceRequest request) {
        stationSpaceUseCase.save(requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }
}
