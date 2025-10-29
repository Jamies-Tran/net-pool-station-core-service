package net.pool.station.core.features.station.space.space.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.space.space.controller.models.StationSpaceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/pub/station-spaces/{stationSpaceId}")
public interface StationSpacePubApi {
    @GetMapping
    MyValueResponse<StationSpaceResponse> findById(@PathVariable Long stationSpaceId);
}
