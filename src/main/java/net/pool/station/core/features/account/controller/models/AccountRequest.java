package net.pool.station.core.features.account.controller.models;

import lombok.Builder;

@Builder
public record AccountRequest(
        String avatar,
        String username,
        String password,
        String identification,
        String phone,
        String email
) {
}
