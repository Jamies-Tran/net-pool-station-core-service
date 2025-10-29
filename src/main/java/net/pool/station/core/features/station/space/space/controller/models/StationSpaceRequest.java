package net.pool.station.core.features.station.space.space.controller.models;

import net.pool.station.core.domain.station.space.StationSpaceId;

public record StationSpaceRequest(
        Long stationId,
        Long spaceId,
        String spaceCode,
        String spaceName,
        Integer capacity,
        String statusCode,
        String statusName
) {
}
