package net.pool.station.core.features.wallet.wallet.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.wallet.wallet.controller.models.WalletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/wallets")
public interface WalletsApi {
    @GetMapping("/me")
    @PreAuthorize("hasAnyRole({'ROLE_PLAYER', 'ROLE_STATION_OWNER'})")
    MyValueResponse<WalletResponse> findByAccountId();
}
