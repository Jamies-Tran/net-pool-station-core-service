package net.pool.station.core.domain.station.space;

import lombok.Builder;

@Builder
public record StationSpaceId(
        Long stationId,
        Long spaceId
) {
    public static StationSpaceId of(Long stationId, Long spaceId) {
        return StationSpaceId.builder()
                .stationId(stationId)
                .spaceId(spaceId)
                .build();
    }
}
