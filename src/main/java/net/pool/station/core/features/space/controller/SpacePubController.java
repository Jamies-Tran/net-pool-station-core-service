package net.pool.station.core.features.space.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainCode;
import net.pool.station.core.domain.space.SpaceUseCase;
import net.pool.station.core.features.space.controller.models.SpaceResponse;
import net.pool.station.core.features.space.controller.models.SpaceResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpacePubController implements SpacePubApi {
    SpaceUseCase spaceUseCase;

    SpaceResponseMapper responseMapper;

    @Override
    public MyValueResponse<SpaceResponse> findById(Long spaceId) {
        SpaceResponse spaceResponse = spaceUseCase.findById(DomainCode.of(spaceId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(spaceResponse);
    }
}
