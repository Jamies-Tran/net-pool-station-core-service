package net.pool.station.core.features.area.area.controller.models;

public record AreaResponse(
        Long areaId,
        Long stationId,
        Long spaceId,
        Long areaTypeId,
        String areaCode,
        String areaName,
        String statusCode,
        String statusName
) {
}
