package net.pool.station.core.features.map.detail.repository.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

public record DetailFeign(
        List<ResultFeign> results
) {
    public record ResultFeign (
            @JsonProperty("formatted_address")
            String address,
            GeometryFeign geometry
    ) {
        @Builder
        public record GeometryFeign(
                LocationFeign location
        ) {
            @Builder
            public record LocationFeign (
                    @JsonProperty("lat")
                    Double latitude,
                    @JsonProperty("lng")
                    Double longitude
            ) {}
        }
    }
}
