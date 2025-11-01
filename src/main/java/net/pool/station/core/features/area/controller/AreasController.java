package net.pool.station.core.features.area.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.area.AreaUseCase;
import net.pool.station.core.features.area.controller.models.AreaRequest;
import net.pool.station.core.features.area.controller.models.AreaRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AreasController implements AreasApi {
    AreaUseCase areaUseCase;

    AreaRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> save(AreaRequest request) {
        areaUseCase.save(requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }
}
