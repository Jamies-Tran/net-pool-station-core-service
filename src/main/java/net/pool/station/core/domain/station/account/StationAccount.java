package net.pool.station.core.domain.station.account;

import lombok.Builder;

@Builder
public record StationAccount(
        StationAccountId stationAccountId,
        String statusCode,
        String statusName
) {
}
