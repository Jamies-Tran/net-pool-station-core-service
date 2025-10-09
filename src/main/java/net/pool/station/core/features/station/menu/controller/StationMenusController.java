package net.pool.station.core.features.station.menu.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.station.menu.StationMenuUseCase;
import net.pool.station.core.features.station.menu.controller.models.StationMenuRequest;
import net.pool.station.core.features.station.menu.controller.models.StationMenuRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationMenusController implements StationMenusApi {
    StationMenuUseCase stationMenuUseCase;

    StationMenuRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> save(StationMenuRequest request) {
        stationMenuUseCase.save(requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }
}
