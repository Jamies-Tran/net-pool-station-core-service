package net.pool.station.core.features.account.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.account.controller.models.AccountRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/pub/accounts")
public interface AccountsPubApi {
    @PostMapping
    MyValueResponse<?> saveForPlayer(@RequestBody @Valid AccountRequest request);
}
