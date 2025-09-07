package net.pool.station.core.features.account.controller.models;

import lombok.Builder;
import lombok.With;

@Builder
public record AccountResponse(
        Long accountId,
        @With Long roleId,
        String avatar,
        String username,
        String password,
        String identification,
        String phone,
        String email,
        String statusCode,
        String statusName
) {
}
