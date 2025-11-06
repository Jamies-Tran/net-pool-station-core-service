package net.pool.station.core.features.login.info.controller.models;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record LoginInfoResponse(
        Long accountId,
        String email,
        String roleCode,
        String accessToken,
        LocalDateTime accessExpiredAt,
        String refreshToken,
        LocalDateTime refreshExpiredAt,
        List<StationResponse> stations
) {
    public record StationResponse(
            String stationId,
            String stationCode,
            String stationName
    ) {}
}
