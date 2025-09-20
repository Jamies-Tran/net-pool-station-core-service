package net.pool.station.core.domain.space;

import lombok.Builder;

@Builder
public record Space(
        Long spaceId,
        Long stationId,
        String spaceCode,
        String spaceName,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName,
        Boolean deleted
) {
}
