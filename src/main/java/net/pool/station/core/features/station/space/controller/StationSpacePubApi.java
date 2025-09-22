package net.pool.station.core.features.station.space.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.space.controller.models.StationSpaceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/pub/station-spaces/{stationSpaceId}")
public interface StationSpacePubApi {
    @GetMapping
    MyValueResponse<StationSpaceResponse> findById(@PathVariable Long stationSpaceId);
}
