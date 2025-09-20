package net.pool.station.core.features.space.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.space.controller.models.SpaceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/pub/spaces/{spaceId}")
public interface SpacePubApi {
    @GetMapping
    MyValueResponse<SpaceResponse> findById(@PathVariable Long spaceId);
}
