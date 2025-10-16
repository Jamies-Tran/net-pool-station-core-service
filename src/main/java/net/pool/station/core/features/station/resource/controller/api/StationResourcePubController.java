package net.pool.station.core.features.station.resource.controller.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.resource.StationResourceUseCase;
import net.pool.station.core.features.station.resource.controller.api.models.StationResourceResponse;
import net.pool.station.core.features.station.resource.controller.api.models.StationResourceResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourcePubController implements StationResourcePubApi {
    StationResourceUseCase stationResourceUseCase;

    StationResourceResponseMapper responseMapper;

    @Override
    public MyValueResponse<StationResourceResponse> findById(Long stationResourceId) {
        StationResourceResponse response = stationResourceUseCase.findById(DomainKey.of(stationResourceId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }
}
