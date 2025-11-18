package net.pool.station.core.features.area.controller.models;

public record AreaResponse(
        Long areaId,
        Long stationSpaceId,
        Integer price,
        String areaCode,
        String areaName,
        String statusCode,
        String statusName
) {
}
