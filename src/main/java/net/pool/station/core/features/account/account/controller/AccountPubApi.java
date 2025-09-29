package net.pool.station.core.features.account.account.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.account.account.controller.models.AccountResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/pub/accounts/{accountId}")
@Tag(name = "Account", description = "QL tài khoản")
public interface AccountPubApi {
    @GetMapping
    MyValueResponse<AccountResponse> findById(@PathVariable Long accountId);
}
