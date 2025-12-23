package net.pool.station.core.features.station.resource.resource.controller.api;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.resource.StationResourceUseCase;
import net.pool.station.core.features.station.resource.resource.controller.api.models.StationResourceListRequest;
import net.pool.station.core.features.station.resource.resource.controller.api.models.StationResourceRequest;
import net.pool.station.core.features.station.resource.resource.controller.api.models.StationResourceRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourcesController implements StationResourcesApi {
    StationResourceUseCase stationResourceUseCase;

    StationResourceRequestMapper mapper;

    @Override
    public MyValueResponse<?> save(StationResourceRequest request) {
        stationResourceUseCase.save(mapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> save(Long areaId, StationResourceListRequest request) {
        stationResourceUseCase.save(new DomainKey<>(areaId), mapper.toDto(request.stationResources()));

        return MyValueResponse.successNoData();
    }
}
