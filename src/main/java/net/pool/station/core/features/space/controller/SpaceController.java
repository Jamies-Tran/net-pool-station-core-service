package net.pool.station.core.features.space.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainCode;
import net.pool.station.core.domain.space.SpaceUseCase;
import net.pool.station.core.features.space.controller.models.SpaceRequest;
import net.pool.station.core.features.space.controller.models.SpaceRequestMapper;
import net.pool.station.core.features.space.controller.models.SpaceResponse;
import net.pool.station.core.features.space.controller.models.SpaceResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpaceController implements SpaceApi {
    SpaceUseCase spaceUseCase;

    SpaceRequestMapper requestMapper;

    SpaceResponseMapper responseMapper;

    @Override
    public MyValueResponse<?> update(Long spaceId, SpaceRequest request) {
        spaceUseCase.update(DomainCode.of(spaceId), requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> enable(Long spaceId) {
        spaceUseCase.enable(DomainCode.of(spaceId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long spaceId) {
        spaceUseCase.disable(DomainCode.of(spaceId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long spaceId) {
        spaceUseCase.delete(DomainCode.of(spaceId));

        return MyValueResponse.successNoData();
    }
}
