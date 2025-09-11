package net.pool.station.core.features.map.place.repository.feign;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.pool.station.core.domain.map.place.Place;

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
