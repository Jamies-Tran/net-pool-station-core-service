package net.pool.station.core.features.station.space.space.controller.models;

import lombok.Builder;
import net.pool.station.core.domain.station.space.StationSpaceId;

@Builder
public record StationSpaceResponse(
        StationSpaceId stationSpaceId,
        String spaceCode,
        String spaceName,
        Integer capacity,
        String statusCode,
        String statusName
) {
}
