package net.pool.station.core.features.login.info.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.login.info.controller.models.LoginInfoResponse;
import net.pool.station.core.features.login.info.controller.models.RefreshTokenRequest;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/auth")
public interface LoginInfoApi {

    @PostMapping("/logout")
    void logout();

    @PatchMapping("/refresh")
    MyValueResponse<LoginInfoResponse> refresh(@RequestBody @Valid RefreshTokenRequest request);
}
