package net.pool.station.core.features.space.controller.models;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SpaceRequest(
        String typeCode,
        @NotNull(message = "Tên loại space không được bỏ trống")
        String typeName,
        MetadataRequest metadata,
        String statusCode,
        String statusName
) {
        public record MetadataRequest(
                String icon,
                String bgColor
        ) {}
}
