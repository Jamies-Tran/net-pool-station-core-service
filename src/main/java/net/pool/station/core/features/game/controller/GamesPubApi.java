package net.pool.station.core.features.game.controller;

import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.features.game.controller.models.GameResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/v1/pub/games")
public interface GamesPubApi {
    @GetMapping
    MyPageResponse<GameResponse> findAll(
            @RequestParam
            Long stationSpaceId,

            @RequestParam(required = false, value = "search", defaultValue = "")
            String search,

            @RequestParam(required = false, value = "genreCodes", defaultValue = "")
            List<String> genreCodes,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "gameName")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );
}
