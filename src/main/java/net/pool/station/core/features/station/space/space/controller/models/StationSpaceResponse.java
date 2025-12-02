package net.pool.station.core.features.station.space.space.controller.models;

import lombok.Builder;
import net.pool.station.core.domain.station.space.StationSpaceId;

import java.util.Map;

@Builder
public record StationSpaceResponse(
        Long stationSpaceId,
        Long stationId,
        Long spaceId,
        String spaceCode,
        String spaceName,
        Integer capacity,
        Boolean allowDirectPayment,
        String statusCode,
        String statusName,
        Map<String, Object> metadata
) {
}
