package net.pool.station.core.features.station.account.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/api/station-account")
public interface StationAccountApi {
    @PatchMapping("/enable")
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> enable(
            @RequestParam Long stationId,
            @RequestParam Long accountId
    );

    @PatchMapping("/disable")
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> disable(
            @RequestParam Long stationId,
            @RequestParam Long accountId
    );
}
