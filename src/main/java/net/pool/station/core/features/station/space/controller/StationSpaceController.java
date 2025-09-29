package net.pool.station.core.features.station.space.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.space.StationSpaceId;
import net.pool.station.core.domain.station.space.StationSpaceUseCase;
import net.pool.station.core.features.station.space.controller.models.StationSpaceRequest;
import net.pool.station.core.features.station.space.controller.models.StationSpaceRequestMapper;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationSpaceController implements StationSpaceApi{
    StationSpaceUseCase stationSpaceUseCase;

    StationSpaceRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> update(Long stationId, Long spaceId, StationSpaceRequest request) {
        StationSpaceId id = StationSpaceId.of(stationId, spaceId);
        stationSpaceUseCase.update(DomainKey.of(id), requestMapper.toDto(request));

        return MyValueResponse.successNoData() ;
    }

    @Override
    public MyValueResponse<?> enable(Long stationId, Long spaceId) {
        StationSpaceId id = StationSpaceId.of(stationId, spaceId);
        stationSpaceUseCase.enable(DomainKey.of(id));

        return MyValueResponse.successNoData() ;
    }

    @Override
    public MyValueResponse<?> disable(Long stationId, Long spaceId) {
        StationSpaceId id = StationSpaceId.of(stationId, spaceId);
        stationSpaceUseCase.disable(DomainKey.of(id));

        return MyValueResponse.successNoData() ;
    }

    @Override
    public MyValueResponse<?> delete(Long stationId, Long spaceId) {
        StationSpaceId id = StationSpaceId.of(stationId, spaceId);
        stationSpaceUseCase.delete(DomainKey.of(id));

        return MyValueResponse.successNoData() ;
    }
}
