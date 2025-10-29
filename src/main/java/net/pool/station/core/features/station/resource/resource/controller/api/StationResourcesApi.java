package net.pool.station.core.features.station.resource.resource.controller.api;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.resource.resource.controller.api.models.StationResourceRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/station-resources")
public interface StationResourcesApi {
    @PostMapping
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> save(@RequestBody @Valid StationResourceRequest request);
}
