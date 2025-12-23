package net.pool.station.core.domain.station.resource;

import lombok.Builder;

@Builder
public record Row(
        String rowCode,
        String rowName
) {
}
