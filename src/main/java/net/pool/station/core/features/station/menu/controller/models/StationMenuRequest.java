package net.pool.station.core.features.station.menu.controller.models;

public record StationMenuRequest(
        Long stationId,
        String menuCode,
        String menuName,
        String typeCode,
        String typeName,
        String description,
        Integer price
) {
}
