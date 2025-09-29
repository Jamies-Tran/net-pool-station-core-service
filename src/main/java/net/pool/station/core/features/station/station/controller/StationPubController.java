package net.pool.station.core.features.station.station.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.StationUseCase;
import net.pool.station.core.features.station.station.controller.models.StationResponse;
import net.pool.station.core.features.station.station.controller.models.StationResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationPubController implements StationPubApi {
    StationUseCase stationUseCase;

    StationResponseMapper responseMapper;

    @Override
    public MyValueResponse<StationResponse> findById(Long stationId) {
        StationResponse stationResponse = stationUseCase.findById(DomainKey.of(stationId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(stationResponse);
    }
}
