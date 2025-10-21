package net.pool.station.core.features.station.resource.controller.internal;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.resource.controller.internal.models.StationResourceRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v2/internal/resource")
public interface StationResourcesApi {
    @PostMapping
    MyValueResponse<?> receiveStationResource(@RequestBody @Valid StationResourceRequest request);
}
