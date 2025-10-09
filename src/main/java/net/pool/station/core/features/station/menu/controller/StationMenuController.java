package net.pool.station.core.features.station.menu.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.menu.StationMenuUseCase;
import net.pool.station.core.features.station.menu.controller.models.StationMenuRequest;
import net.pool.station.core.features.station.menu.controller.models.StationMenuRequestMapper;
import net.pool.station.core.features.station.menu.controller.models.StationMenuResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationMenuController implements StationMenuApi{
    StationMenuUseCase stationMenuUseCase;

    StationMenuRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> update(Long stationMenuId, StationMenuRequest request) {
        stationMenuUseCase.update(DomainKey.of(stationMenuId), requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> enable(Long stationMenuId) {
        stationMenuUseCase.enable(DomainKey.of(stationMenuId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> disable(Long stationMenuId) {
        stationMenuUseCase.disable(DomainKey.of(stationMenuId));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> delete(Long stationMenuId) {
        stationMenuUseCase.delete(DomainKey.of(stationMenuId));

        return MyValueResponse.successNoData();
    }
}
