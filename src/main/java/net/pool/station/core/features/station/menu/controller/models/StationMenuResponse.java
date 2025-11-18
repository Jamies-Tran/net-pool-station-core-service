package net.pool.station.core.features.station.menu.controller.models;

public record StationMenuResponse(
        Long stationMenuId,
        Long stationId,
        String menuCode,
        String menuName,
        String typeCode,
        String typeName,
        String description,
        Integer price,
        String statusCode,
        String statusName
) {
}
