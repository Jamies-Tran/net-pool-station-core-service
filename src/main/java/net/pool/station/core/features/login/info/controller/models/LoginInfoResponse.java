package net.pool.station.core.features.login.info.controller.models;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LoginInfoResponse(
        Long accountId,
        String email,
        String accessToken,
        LocalDateTime accessExpiredAt,
        String refreshToken,
        LocalDateTime refreshExpiredAt,
        Double latitude,
        Double longitude
) {
}
