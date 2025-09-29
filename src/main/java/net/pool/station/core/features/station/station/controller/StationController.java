package net.pool.station.core.features.station.station.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.StationUseCase;
import net.pool.station.core.features.station.station.controller.models.StationRejectRequest;
import net.pool.station.core.features.station.station.controller.models.StationRequest;
import net.pool.station.core.features.station.station.controller.models.StationRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationController implements StationApi {
    StationUseCase stationUseCase;

    StationRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> update(Long stationId, StationRequest request) {
        stationUseCase.update(DomainKey.of(stationId), requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> accept(Long stationId) {
        stationUseCase.accept(DomainKey.of(stationId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> reject(Long stationId, StationRejectRequest request) {
        stationUseCase.reject(DomainKey.of(stationId), request.rejectReason());

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> enable(Long stationId) {
        stationUseCase.enable(DomainKey.of(stationId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long stationId) {
        stationUseCase.disable(DomainKey.of(stationId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long stationId) {
        stationUseCase.delete(DomainKey.of(stationId));

        return MyValueResponse.successNoData();
    }
}
