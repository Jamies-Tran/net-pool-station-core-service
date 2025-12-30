package net.pool.station.core.features.category.controller.models;

import lombok.Builder;

@Builder
public record CategoryResponse(
        String code,
        String name,
        String type
) {
}
