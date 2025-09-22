package net.pool.station.core.features.station.space.controller.models;

import lombok.Builder;

@Builder
public record StationSpaceResponse(
        Long stationSpaceId,
        Long stationId,
        Long spaceId,
        String spaceCode,
        String spaceName,
        String statusCode,
        String statusName
) {
}
