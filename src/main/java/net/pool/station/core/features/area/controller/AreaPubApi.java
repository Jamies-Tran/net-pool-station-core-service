package net.pool.station.core.features.area.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.area.controller.models.AreaResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/pub/areas/{areaId}")
public interface AreaPubApi {
    MyValueResponse<AreaResponse> findById(@PathVariable Long areaId);
}
