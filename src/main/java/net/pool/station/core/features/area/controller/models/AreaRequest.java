package net.pool.station.core.features.area.controller.models;

public record AreaRequest(
        Long stationSpaceId,
        String areaCode,
        String areaName
) {
}
