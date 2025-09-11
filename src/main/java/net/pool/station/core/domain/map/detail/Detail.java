package net.pool.station.core.domain.map.detail;

import lombok.Builder;

import java.util.List;

public record Detail(
        List<Result> results
) {
    public record Result(
            String address,
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
