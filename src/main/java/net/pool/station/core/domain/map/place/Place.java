package net.pool.station.core.domain.map.place;

import java.util.List;

public record Place(
        List<Prediction> predictions
) {
    public record Prediction(
            String address,
            String placeId,
            Compound compound
    ) {
        public record Compound(
                String district,
                String commune,
                String province
        ) {}
    }
}
