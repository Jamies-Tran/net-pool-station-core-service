package net.pool.station.core.domain.station.resource;

import lombok.With;
import net.pool.station.core.bootstrap.enums.EResourceType;
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
        Integer playerCount,
        Boolean allowDirectPayment,
        String statusCode,
        String statusName,
        String rowCode,
        String rowName,
        Integer displayOrder
) {
    public StationResource {
        playerCount = Integer.valueOf(EResourceType.valueOf(typeCode).getType());
    }

    public record AreaId(
            Long areaId
    ) {}
}
