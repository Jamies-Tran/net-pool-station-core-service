package net.pool.station.core.domain.station.space;

import lombok.With;

import java.util.Map;

public record StationSpace(
        Long stationSpaceId,
        Long stationId,
        Long spaceId,
        String spaceCode,
        String spaceName,
        Integer capacity,
        @With Boolean allowDirectPayment,
        String statusCode,
        String statusName,
        Boolean deleted,
        @With Map<String, Object> metadata
) {
}
