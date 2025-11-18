package net.pool.station.core.domain.station.menu;

import lombok.Builder;

@Builder
public record StationMenu(
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
