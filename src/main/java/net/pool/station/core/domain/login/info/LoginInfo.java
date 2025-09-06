package net.pool.station.core.domain.login.info;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record LoginInfo(
        Long loginInfoId,
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
