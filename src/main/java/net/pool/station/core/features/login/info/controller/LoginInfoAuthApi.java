package net.pool.station.core.features.login.info.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.login.info.controller.models.LoginInfoRequest;
import net.pool.station.core.features.login.info.controller.models.LoginInfoResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/auth")
public interface LoginInfoAuthApi {
    @PostMapping("/login")
    MyValueResponse<LoginInfoResponse> login(@RequestBody @Valid LoginInfoRequest request);
}
