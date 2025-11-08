package net.pool.station.core.features.wallet.wallet.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.wallet.wallet.controller.models.WalletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/wallets/{accountId}")
public interface WalletApi {
    @GetMapping
    @PreAuthorize("hasAnyRole({'ROLE_PLAYER', 'ROLE_STATION_OWNER', 'ROLE_PLATFORM_ADMIN'})")
    MyValueResponse<WalletResponse> findByAccountId(@PathVariable Long accountId);

    @PatchMapping("/enable")
    @PreAuthorize("hasRole({'ROLE_PLATFORM_ADMIN'})")
    MyValueResponse<?> enable(@PathVariable Long accountId);

    @PatchMapping("/disable")
    @PreAuthorize("hasRole({'ROLE_PLATFORM_ADMIN'})")
    MyValueResponse<?> disable(@PathVariable Long accountId);
}
