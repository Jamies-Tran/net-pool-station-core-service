package net.pool.station.core.features.account.account.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.account.account.controller.models.AccountRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/accounts")
@Tag(name = "Account", description = "QL tài khoản")
public interface AccountsApi {

    @PostMapping("/platform-admin")
    @PreAuthorize("hasRole({'ROLE_SYSTEM_ADMIN'})")
    MyValueResponse<?> saveForPlatformAdmin(@RequestBody @Valid AccountRequest request);

    @PostMapping("/station-admin")
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> saveForStationAdmin(@RequestBody @Valid AccountRequest request);
}
