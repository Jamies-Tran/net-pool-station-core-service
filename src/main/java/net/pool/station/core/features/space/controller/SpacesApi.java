package net.pool.station.core.features.space.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.space.controller.models.SpaceRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/spaces")
public interface SpacesApi {
    @PostMapping
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> save(@RequestBody @Valid SpaceRequest request);
}
