package net.pool.station.core.features.map.place.controller.models;

import net.pool.station.core.domain.map.place.Place;

import java.util.List;

public record PlaceResponse(
       List<PredictionResponse> predictions
) {
    public record PredictionResponse(
            String address,
            String placeId,
            CompoundResponse compound
    ) {
        public record CompoundResponse(
                String district,
                String commune,
                String province
        ) {}
    }
}
