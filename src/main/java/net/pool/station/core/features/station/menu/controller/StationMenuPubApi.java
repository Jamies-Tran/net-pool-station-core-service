package net.pool.station.core.features.station.menu.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.menu.controller.models.StationMenuResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/pub/station-menus/{stationMenuId}")
public interface StationMenuPubApi {
    @GetMapping
    MyValueResponse<StationMenuResponse> findBydId(@PathVariable Long stationMenuId);
}
