package net.pool.station.core.features.station.resource.resource.controller.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.resource.StationResourceUseCase;
import net.pool.station.core.features.station.resource.resource.controller.api.models.StationResourceRequest;
import net.pool.station.core.features.station.resource.resource.controller.api.models.StationResourceRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceController implements StationResourceApi {
    StationResourceUseCase stationResourceUseCase;

    StationResourceRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> update(Long stationResourceId, StationResourceRequest request) {
        stationResourceUseCase.update(DomainKey.of(stationResourceId), requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> enable(Long stationResourceId) {
        stationResourceUseCase.enable(DomainKey.of(stationResourceId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long stationResourceId) {
        stationResourceUseCase.disable(DomainKey.of(stationResourceId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long stationResourceId) {
        stationResourceUseCase.delete(DomainKey.of(stationResourceId));

        return MyValueResponse.successNoData();
    }
}
