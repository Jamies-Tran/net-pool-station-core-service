package net.pool.station.core.features.station.space.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainCode;
import net.pool.station.core.domain.station.space.StationSpaceUseCase;
import net.pool.station.core.features.station.space.controller.models.StationSpaceRequest;
import net.pool.station.core.features.station.space.controller.models.StationSpaceRequestMapper;
import net.pool.station.core.features.station.space.controller.models.StationSpaceResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceController implements StationSpaceApi{
    StationSpaceUseCase stationSpaceUseCase;

    StationSpaceRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> update(Long stationSpaceId, StationSpaceRequest request) {
        stationSpaceUseCase.update(DomainCode.of(stationSpaceId), requestMapper.toDto(request));

        return MyValueResponse.successNoData() ;
    }

    @Override
    public MyValueResponse<?> enable(Long stationSpaceId) {
        stationSpaceUseCase.enable(DomainCode.of(stationSpaceId));

        return MyValueResponse.successNoData() ;
    }

    @Override
    public MyValueResponse<?> disable(Long stationSpaceId) {
        stationSpaceUseCase.disable(DomainCode.of(stationSpaceId));

        return MyValueResponse.successNoData() ;
    }

    @Override
    public MyValueResponse<?> delete(Long stationSpaceId) {
        stationSpaceUseCase.delete(DomainCode.of(stationSpaceId));

        return MyValueResponse.successNoData() ;
    }
}
