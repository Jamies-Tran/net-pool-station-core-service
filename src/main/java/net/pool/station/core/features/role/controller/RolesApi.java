package net.pool.station.core.features.role.controller;

import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.features.role.controller.models.RoleResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/pub/roles")
public interface RolesApi {
    @GetMapping
    MyListResponse<RoleResponse> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "") String search
    );
}
