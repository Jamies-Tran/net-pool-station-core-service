package net.pool.station.core.domain.station.resource;

import lombok.With;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;

public record StationResource(
        Long stationResourceId,
        @With Long areaId,
        @With StationResourceSpec spec,
        Integer price,
        String resourceCode,
        String resourceName,
        String typeCode,
        String typeName,
        Boolean allowDirectPayment,
        String statusCode,
        String statusName
) {
    public record AreaId(
            Long areaId
    ) {}
}
