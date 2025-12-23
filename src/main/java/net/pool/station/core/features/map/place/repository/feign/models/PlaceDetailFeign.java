package net.pool.station.core.features.map.place.repository.feign.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PlaceDetailFeign(
    ResultFeign result
) {
    public record ResultFeign(
        GeometryFeign geometry
    ) {
        public record GeometryFeign(
                LocationFeign location
        ) {
            public record LocationFeign(
                    @JsonProperty("lat")
                    Double latitude,
                    @JsonProperty("lng")
                    Double longitude
            ) {}
        }
    }
}
