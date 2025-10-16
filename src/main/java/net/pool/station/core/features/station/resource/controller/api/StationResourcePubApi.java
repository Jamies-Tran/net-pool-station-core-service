package net.pool.station.core.features.station.resource.controller.api;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.resource.controller.api.models.StationResourceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/pub/station-resources/{stationResourceId}")
public interface StationResourcePubApi {
    @GetMapping
    MyValueResponse<StationResourceResponse> findById(@PathVariable Long stationResourceId);
}
