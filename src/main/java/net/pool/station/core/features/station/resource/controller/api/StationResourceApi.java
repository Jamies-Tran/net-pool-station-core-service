package net.pool.station.core.features.station.resource.controller.api;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.resource.controller.api.models.StationResourceRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/station-resources/{stationResourceId}")
public interface StationResourceApi {
    @PutMapping
    @PreAuthorize("hasAnyRole({'ROLE_STATION_OWNER', 'ROLE_STATION_ADMIN'})")
    MyValueResponse<?> update(@PathVariable Long stationResourceId, @RequestBody @Valid StationResourceRequest request);

    @PatchMapping("/enable")
    @PreAuthorize("hasAnyRole({'ROLE_STATION_OWNER', 'ROLE_STATION_ADMIN'})")
    MyValueResponse<?> enable(@PathVariable Long stationResourceId);

    @PatchMapping("/disable")
    @PreAuthorize("hasAnyRole({'ROLE_STATION_OWNER', 'ROLE_STATION_ADMIN'})")
    MyValueResponse<?> disable(@PathVariable Long stationResourceId);

    @PatchMapping("/delete")
    @PreAuthorize("hasAnyRole({'ROLE_STATION_OWNER', 'ROLE_STATION_ADMIN'})")
    MyValueResponse<?> delete(@PathVariable Long stationResourceId);
}
