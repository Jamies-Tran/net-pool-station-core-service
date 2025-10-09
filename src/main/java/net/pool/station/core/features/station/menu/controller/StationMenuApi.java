package net.pool.station.core.features.station.menu.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.menu.controller.models.StationMenuRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/station-menus/{stationMenuId}")
public interface StationMenuApi {
    @PutMapping
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> update(@PathVariable Long stationMenuId, @RequestBody @Valid StationMenuRequest request);

    @PatchMapping("/enable")
    @PreAuthorize("hasAnyRole({'ROLE_STATION_OWNER', 'ROLE_STATION_ADMIN'})")
    MyValueResponse<?> enable(@PathVariable Long stationMenuId);

    @PatchMapping("/disable")
    @PreAuthorize("hasAnyRole({'ROLE_STATION_OWNER', 'ROLE_STATION_ADMIN'})")
    MyValueResponse<?> disable(@PathVariable Long stationMenuId);

    @DeleteMapping
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> delete(@PathVariable Long stationMenuId);
}
