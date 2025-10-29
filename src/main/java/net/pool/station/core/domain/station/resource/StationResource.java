package net.pool.station.core.domain.station.resource;

import lombok.With;

public record StationResource(
        Long stationResourceId,
        @With Long areaId,
        String resourceCode,
        String resourceName,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName
) {
    public record AreaId(
            Long areaId
    ) {}
}
