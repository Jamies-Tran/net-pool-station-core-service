package net.pool.station.core.features.space.controller.models;

import lombok.Builder;

@Builder
public record SpaceResponse(
        Long spaceId,
        Long stationId,
        String spaceCode,
        String spaceName,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName
) {
}
