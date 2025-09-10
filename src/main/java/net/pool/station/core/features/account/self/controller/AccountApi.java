package net.pool.station.core.features.account.self.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.account.self.controller.models.AccountRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/accounts/{accountId}")
@Tag(name = "Account", description = "QL tài khoản")
public interface AccountApi {
    @PutMapping
    MyValueResponse<?> update(@PathVariable Long accountId, @RequestBody @Valid AccountRequest request);

    @PatchMapping("/enable")
    MyValueResponse<?> enable(@PathVariable Long accountId);

    @PatchMapping("/disable")
    MyValueResponse<?> disable(@PathVariable Long accountId);

    @DeleteMapping
    @PreAuthorize("hasRole({'ROLE_SYSTEM_ADMIN'})")
    MyValueResponse<?> delete(@PathVariable Long accountId);
}
