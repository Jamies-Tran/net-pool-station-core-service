package net.pool.station.core.domain.station.space;

public record StationSpace(
        StationSpaceId stationSpaceId,
        String spaceCode,
        String spaceName,
        Integer capacity,
        String statusCode,
        String statusName,
        Boolean deleted
) {
}
