package net.pool.station.core.features.station.resource.resource.controller.api.models;

import lombok.Builder;
import net.pool.station.core.domain.station.resource.StationResource;

import java.util.List;

public record StationResourceResponse(
        Long stationResourceId,
        Long areaId,
        String resourceCode,
        String resourceName,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName
) {

}
