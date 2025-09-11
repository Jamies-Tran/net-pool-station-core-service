package net.pool.station.core.features.map.reverse.repository.feign;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ReverseGeoFeign(
        List<ResultFeign> results
) {
    public record ResultFeign(
            @JsonProperty("formatted_address")
            String address
    ) {}
}
