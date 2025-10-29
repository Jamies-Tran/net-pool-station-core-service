package net.pool.station.core.features.station.space.space.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.space.space.controller.models.StationSpaceRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/api/station-spaces/{stationSpaceId}")
public interface StationSpaceApi {
    @PutMapping
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> update(@PathVariable Long stationSpaceId,
                              @RequestBody @Valid StationSpaceRequest request);

    @PatchMapping("/enable")
    @PreAuthorize("hasAnyRole({'ROLE_STATION_OWNER', 'ROLE_STATION_ADMIN'})")
    MyValueResponse<?> enable(@PathVariable Long stationSpaceId);

    @PatchMapping("/disable")
    @PreAuthorize("hasAnyRole({'ROLE_STATION_OWNER', 'ROLE_STATION_ADMIN'})")
    MyValueResponse<?> disable(@PathVariable Long stationSpaceId);

    @DeleteMapping
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> delete(@PathVariable Long stationSpaceId);
}
