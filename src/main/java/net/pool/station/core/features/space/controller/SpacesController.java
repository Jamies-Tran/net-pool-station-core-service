package net.pool.station.core.features.space.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.space.SpaceUseCase;
import net.pool.station.core.features.space.controller.models.SpaceRequest;
import net.pool.station.core.features.space.controller.models.SpaceRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SpacesController implements SpacesApi {
    SpaceUseCase spaceUseCase;

    SpaceRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> save(SpaceRequest request) {
        spaceUseCase.save(requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }
}
