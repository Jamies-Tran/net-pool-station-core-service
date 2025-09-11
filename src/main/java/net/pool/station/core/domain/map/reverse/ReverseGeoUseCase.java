package net.pool.station.core.domain.map.reverse;

import java.util.Optional;

public interface ReverseGeoUseCase {
    Optional<ReverseGeo.Result> reverseGeo(Double latitude, Double longitude);
}
