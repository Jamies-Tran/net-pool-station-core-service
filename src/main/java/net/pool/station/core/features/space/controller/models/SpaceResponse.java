package net.pool.station.core.features.space.controller.models;

import lombok.Builder;

import java.util.Map;

@Builder
public record SpaceResponse(
        Long spaceId,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName,
        Map<String, Object> metadata
) {
}
