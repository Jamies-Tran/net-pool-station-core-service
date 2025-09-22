package net.pool.station.core.domain.station.space;

public record StationSpace(
        Long stationSpaceId,
        Long stationId,
        Long spaceId,
        String spaceCode,
        String spaceName,
        String statusCode,
        String statusName,
        Boolean deleted
) {
}
