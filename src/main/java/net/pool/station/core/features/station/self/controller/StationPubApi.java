package net.pool.station.core.features.station.self.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.self.controller.models.StationResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/pub/stations/{stationId}")
public interface StationPubApi {
    @GetMapping
    MyValueResponse<StationResponse> findById(@PathVariable Long stationId);
}
