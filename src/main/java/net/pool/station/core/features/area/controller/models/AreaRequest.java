package net.pool.station.core.features.area.controller.models;

public record AreaRequest(
        Long stationSpaceId,
        Integer price,
        String areaCode,
        String areaName
) {
}
