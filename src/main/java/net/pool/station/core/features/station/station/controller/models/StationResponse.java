package net.pool.station.core.features.station.station.controller.models;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.domain.media.Media;
import net.pool.station.core.domain.station.Station;

import java.time.LocalDateTime;
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
        Double latitude,
        Double longitude,
        Double distance,
        String hotline,
        List<Media> media,
        String rejectReason,
        LocalDateTime rejectAt,
        String statusCode,
        String statusName
) {
}
