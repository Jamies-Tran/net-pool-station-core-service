package net.pool.station.core.features.station.self.controller.models;

import lombok.Builder;
import net.pool.station.core.domain.media.Media;
import net.pool.station.core.domain.station.Station;

import java.util.List;

@Builder
public record StationResponse(
        Long stationId,
        String avatar,
        String stationCode,
        String stationName,
        String address,
        String province,
        String commune,
        String district,
        String hotline,
        List<Media> media,
        Station.Metadata metadata,
        String statusCode,
        String statusName
) {
}
