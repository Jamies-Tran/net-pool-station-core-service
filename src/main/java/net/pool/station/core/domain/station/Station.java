package net.pool.station.core.domain.station;

import lombok.Builder;
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
        String hotline,
        List<Media> media,
        Metadata metadata,
        String statusCode,
        String statusName
) {
    @Builder
    public record Metadata(
            String rejectReason,
            LocalDateTime rejectAt
    ) {
        public static Metadata empty() {
            return Metadata.builder()
                    .rejectReason("")
                    .rejectAt(null)
                    .build();
        }
    }
}
