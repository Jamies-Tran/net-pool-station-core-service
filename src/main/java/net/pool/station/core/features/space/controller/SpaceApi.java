package net.pool.station.core.features.space.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.space.controller.models.SpaceRequest;
import net.pool.station.core.features.space.controller.models.SpaceResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/spaces/{spaceId}")
public interface SpaceApi {
    @PutMapping
    @PreAuthorize("hasRole({'ROLE_PLATFORM_ADMIN'})")
    MyValueResponse<?> update(@PathVariable Long spaceId, @RequestBody @Valid SpaceRequest request);

    @PatchMapping("/enable")
    @PreAuthorize("hasRole({'ROLE_PLATFORM_ADMIN'})")
    MyValueResponse<?> enable(@PathVariable Long spaceId);

    @PatchMapping("/disable")
    @PreAuthorize("hasRole({'ROLE_PLATFORM_ADMIN'})")
    MyValueResponse<?> disable(@PathVariable Long spaceId);

    @DeleteMapping
    @PreAuthorize("hasRole({'ROLE_PLATFORM_ADMIN'})")
    MyValueResponse<?> delete(@PathVariable Long spaceId);
}
