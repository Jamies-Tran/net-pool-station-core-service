package net.pool.station.core.domain.area;

public record Area(
        Long areaId,
        Long stationSpaceId,
        Integer price,
        String areaCode,
        String areaName,
        String statusCode,
        String statusName
) {
}
