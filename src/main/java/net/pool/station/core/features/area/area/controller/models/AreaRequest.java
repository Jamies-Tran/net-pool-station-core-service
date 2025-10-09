package net.pool.station.core.features.area.area.controller.models;

public record AreaRequest(
        Long stationId,
        Long spaceId,
        Long areaTypeId,
        String areaCode,
        String areaName
) {
}
