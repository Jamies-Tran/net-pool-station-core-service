package net.pool.station.core.features.account.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.account.controller.models.AccountRequest;
import net.pool.station.core.features.account.controller.models.AccountResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/v1/pub/accounts")
@Tag(name = "Account", description = "QL tài khoản")
public interface AccountsPubApi {
    @PostMapping
    MyValueResponse<?> saveForPlayer(@RequestBody @Valid AccountRequest request);

    @PostMapping("/station-owner")
    MyValueResponse<?> saveForStationOwner(@RequestBody @Valid AccountRequest request);

    @GetMapping
    MyPageResponse<AccountResponse> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "")
            String search,

            @RequestParam(required = false, value = "timeRange", defaultValue = "")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            List<LocalDateTime> timeRange,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "roleIds", defaultValue = "")
            List<Long> roleIds,

            @RequestParam(required = false, value = "sorter", defaultValue = "createdAt")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "current", defaultValue = "25")
            Integer pageSize
    );
}
