package net.pool.station.core.domain.station.account;

import lombok.Builder;

@Builder
public record StationAccountId(
        Long stationId,
        Long accountId
) {
    public static StationAccountId of(Long stationId, Long accountId) {
        return StationAccountId.builder()
                .stationId(stationId)
                .accountId(accountId)
                .build();
    }
}
