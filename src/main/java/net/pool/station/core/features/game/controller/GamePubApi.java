package net.pool.station.core.features.game.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.game.controller.models.GameResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/pub/games/{gameId}")
public interface GamePubApi {
    @GetMapping
    MyValueResponse<GameResponse> findById(@PathVariable Long gameId);
}
