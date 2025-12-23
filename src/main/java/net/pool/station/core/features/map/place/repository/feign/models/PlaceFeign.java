package net.pool.station.core.features.map.place.repository.feign.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PlaceFeign(
        List<PredictionFeign> predictions
) {
    public record PredictionFeign (
            @JsonProperty("description")
            String address,
            @JsonProperty("place_id")
            String placeId,
            CompoundFeign compound
    ) {
        public record CompoundFeign(
                String district,
                String commune,
                String province
        ) {}
    }
}
