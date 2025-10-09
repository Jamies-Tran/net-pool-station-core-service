package net.pool.station.core.features.station.menu.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.menu.StationMenuUseCase;
import net.pool.station.core.features.station.menu.controller.models.StationMenuResponse;
import net.pool.station.core.features.station.menu.controller.models.StationMenuResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationMenuPubController implements StationMenuPubApi {
    StationMenuUseCase stationMenuUseCase;

    StationMenuResponseMapper responseMapper;

    @Override
    public MyValueResponse<StationMenuResponse> findBydId(Long stationMenuId) {
        StationMenuResponse response = stationMenuUseCase.findById(DomainKey.of(stationMenuId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }
}
