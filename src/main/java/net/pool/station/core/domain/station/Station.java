package net.pool.station.core.domain.station;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.domain.media.Media;

import java.time.LocalDateTime;
import java.util.List;

public record Station(
        Long stationId,
        String avatar,
        String stationCode,
        String stationName,
        String address,
        String province,
        String commune,
        String district,
        String placeId,
        @With Double latitude,
        @With Double longitude,
        @With Double distance,
        String hotline,
        @With List<Media> media,
        String rejectReason,
        LocalDateTime rejectAt,
        String statusCode,
        String statusName
) {
}
