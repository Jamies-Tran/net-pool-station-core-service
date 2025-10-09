package net.pool.station.core.features.station.menu.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.menu.controller.models.StationMenuRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/station-menus")
public interface StationMenusApi {
    @PostMapping
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> save(@RequestBody @Valid StationMenuRequest request);
}
