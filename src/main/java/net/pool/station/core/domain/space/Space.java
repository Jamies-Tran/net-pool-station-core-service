package net.pool.station.core.domain.space;

import lombok.Builder;

import java.util.Map;

@Builder
public record Space(
        Long spaceId,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName,
        Map<String, Object> metadata,
        Boolean deleted
) {
}
