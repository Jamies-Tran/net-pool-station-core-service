package net.pool.station.core.features.station.self.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.self.controller.models.StationRejectRequest;
import net.pool.station.core.features.station.self.controller.models.StationRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/stations/{stationId}")
public interface StationApi {
    @PutMapping
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> update(
            @PathVariable Long stationId,
            @RequestBody @Valid StationRequest request
    );

    @PatchMapping("/accept")
    @PreAuthorize("hasRole({'ROLE_PLATFORM_ADMIN'})")
    MyValueResponse<?> accept(@PathVariable Long stationId);

    @PatchMapping("/reject")
    @PreAuthorize("hasRole({'ROLE_PLATFORM_ADMIN'})")
    MyValueResponse<?> reject(
            @PathVariable Long stationId,
            @RequestBody @Valid StationRejectRequest request
    );

    @PatchMapping("/enable")
    @PreAuthorize("hasAnyRole({'ROLE_PLATFORM_ADMIN', 'ROLE_STATION_OWNER'})")
    MyValueResponse<?> enable(@PathVariable Long stationId);

    @PatchMapping("/disable")
    @PreAuthorize("hasAnyRole({'ROLE_PLATFORM_ADMIN', 'ROLE_STATION_OWNER'})")
    MyValueResponse<?> disable(@PathVariable Long stationId);

    @DeleteMapping
    MyValueResponse<?> delete(@PathVariable Long stationId);
}
