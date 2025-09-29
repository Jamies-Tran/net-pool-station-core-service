package net.pool.station.core.features.area.type.controller.models;

import lombok.Builder;

@Builder
public record AreaTypeRequest(
        String typeCode,
        String typeName,
        String statusCode,
        String statusName
) {
}
