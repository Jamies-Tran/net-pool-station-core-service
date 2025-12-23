package net.pool.station.core.domain.map.place.detail;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import net.pool.station.core.features.map.place.repository.feign.models.PlaceDetailFeign;

@Builder
public record PlaceDetail(
        Result result
) {
    public static PlaceDetail defaultPlaceDetail() {
        return PlaceDetail.builder()
                .result(Result.builder()
                        .geometry(Result.Geometry.builder()
                                .location(Result.Geometry.Location.builder()
                                        .latitude(0.0)
                                        .longitude(0.0)
                                        .build())
                                .build())
                        .build())
                .build();
    }

    @Builder
    public record Result(
            Geometry geometry
    ) {
        @Builder
        public record Geometry(
                Location location
        ) {
            @Builder
            public record Location(
                    Double latitude,
                    Double longitude
            ) {}
        }
    }
}
