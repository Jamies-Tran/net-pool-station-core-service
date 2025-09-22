package net.pool.station.core.features.station.space.controller.models;

public record StationSpaceRequest(
        Long stationId,
        Long spaceId,
        String spaceCode,
        String spaceName,
        String statusCode,
        String statusName
) {
}
