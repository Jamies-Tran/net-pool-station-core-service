package net.pool.station.core.features.station.space.controller.models;

import net.pool.station.core.domain.station.space.StationSpaceId;

public record StationSpaceRequest(
        StationSpaceId stationSpaceId,
        String spaceCode,
        String spaceName,
        Integer capacity,
        String statusCode,
        String statusName
) {
}
