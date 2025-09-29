package net.pool.station.core.features.station.space.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.space.StationSpaceId;
import net.pool.station.core.domain.station.space.StationSpaceUseCase;
import net.pool.station.core.features.station.space.controller.models.StationSpaceResponse;
import net.pool.station.core.features.station.space.controller.models.StationSpaceResponseMapper;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpacePubController implements StationSpacePubApi {
    StationSpaceUseCase stationSpaceUseCase;

    StationSpaceResponseMapper responseMapper;

    @Override
    public MyValueResponse<StationSpaceResponse> findById(Long stationId, Long spaceId) {
        StationSpaceId id = StationSpaceId.of(stationId, spaceId);
        StationSpaceResponse response = stationSpaceUseCase.findById(DomainKey.of(id))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }
}
