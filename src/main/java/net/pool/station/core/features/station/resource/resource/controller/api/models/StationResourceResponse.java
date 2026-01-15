package net.pool.station.core.features.station.resource.resource.controller.api.models;

import lombok.Builder;
import net.pool.station.core.domain.station.resource.StationResource;
import net.pool.station.core.features.station.resource.specs.controller.models.StationResourceSpecResponse;

import java.util.List;

public record StationResourceResponse(
        Long stationResourceId,
        Long areaId,
        StationResourceSpecResponse spec,
        String resourceCode,
        String resourceName,
        Integer price,
        String typeCode,
        String typeName,
        Boolean allowDirectPayment,
        String rowCode,
        String rowName,
        Integer displayOrder,
        String statusCode,
        String statusName
) {

}
