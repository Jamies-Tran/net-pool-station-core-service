package net.pool.station.core.features.account.account.controller.models;

import lombok.Builder;
import lombok.With;

import java.util.List;

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
        String statusName,
        List<StationResponse> stations
) {
    public record StationResponse (
            String stationId,
            String stationCode,
            String stationName
    ) {}
}
