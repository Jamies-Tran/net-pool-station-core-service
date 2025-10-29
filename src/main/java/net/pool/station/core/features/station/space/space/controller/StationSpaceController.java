package net.pool.station.core.features.station.space.space.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.space.StationSpaceId;
import net.pool.station.core.domain.station.space.StationSpaceUseCase;
import net.pool.station.core.features.station.space.space.controller.models.StationSpaceRequest;
import net.pool.station.core.features.station.space.space.controller.models.StationSpaceRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceController implements StationSpaceApi{
    StationSpaceUseCase stationSpaceUseCase;

    StationSpaceRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> update(Long stationSpaceId, StationSpaceRequest request) {
        stationSpaceUseCase.update(DomainKey.of(stationSpaceId), requestMapper.toDto(request));

        return MyValueResponse.successNoData() ;
    }

    @Override
    public MyValueResponse<?> enable(Long stationSpaceId) {
        stationSpaceUseCase.enable(DomainKey.of(stationSpaceId));

        return MyValueResponse.successNoData() ;
    }

    @Override
    public MyValueResponse<?> disable(Long stationSpaceId) {
        stationSpaceUseCase.disable(DomainKey.of(stationSpaceId));

        return MyValueResponse.successNoData() ;
    }

    @Override
    public MyValueResponse<?> delete(Long stationSpaceId) {
        stationSpaceUseCase.delete(DomainKey.of(stationSpaceId));

        return MyValueResponse.successNoData() ;
    }
}
