package net.pool.station.core.features.game.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.game.controller.models.GameRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/games")
@PreAuthorize("hasAnyRole({'ROLE_STATION_OWNER', 'ROLE_STATION_ADMIN'})")
public interface GamesApi {
    @PostMapping
    MyValueResponse<?> save(@RequestBody @Valid GameRequest request);
}
