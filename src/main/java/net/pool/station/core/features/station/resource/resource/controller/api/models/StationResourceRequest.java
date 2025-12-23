package net.pool.station.core.features.station.resource.resource.controller.api.models;

import lombok.Builder;
import net.pool.station.core.domain.station.resource.StationResource;
import net.pool.station.core.features.station.resource.specs.controller.models.StationResourceSpecRequest;

import java.util.List;

public record StationResourceRequest(
        Long areaId,
        String resourceCode,
        String resourceName,
        String typeCode,
        String typeName,
        String rowCode,
        String rowName,
        Integer displayOrder,
        StationResourceSpecRequest spec
) {
    public StationResourceRequest {
        spec = spec
                .withTypeCode(typeCode)
                .withTypeName(typeName);
    }
}
