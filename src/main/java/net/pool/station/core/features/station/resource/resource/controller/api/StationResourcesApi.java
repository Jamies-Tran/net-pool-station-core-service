package net.pool.station.core.features.station.resource.resource.controller.api;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyMapResponse;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.station.resource.Row;
import net.pool.station.core.features.station.resource.resource.controller.api.models.StationResourceListRequest;
import net.pool.station.core.features.station.resource.resource.controller.api.models.StationResourceRequest;
import net.pool.station.core.features.station.resource.resource.controller.api.models.StationResourceResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/v1/api/station-resources")
public interface StationResourcesApi {
    @PostMapping
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> save(@RequestBody @Valid StationResourceRequest request);

    @PostMapping("/{areaId}")
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> save(@PathVariable Long areaId,
                            @RequestBody @Valid StationResourceListRequest request);
}
