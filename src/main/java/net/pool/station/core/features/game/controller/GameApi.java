package net.pool.station.core.features.game.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.game.controller.models.GameRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/games/{gameId}")
@PreAuthorize("hasAnyRole({'ROLE_STATION_OWNER', 'ROLE_STATION_ADMIN'})")
public interface GameApi {
    @PutMapping
    MyValueResponse<?> update(@PathVariable Long gameId, @RequestBody @Valid GameRequest request);

    @PatchMapping("/enable")
    MyValueResponse<?> enable(@PathVariable Long gameId);

    @PatchMapping("/disable")
    MyValueResponse<?> disable(@PathVariable Long gameId);

    @DeleteMapping
    MyValueResponse<?> delete(@PathVariable Long gameId);
}
