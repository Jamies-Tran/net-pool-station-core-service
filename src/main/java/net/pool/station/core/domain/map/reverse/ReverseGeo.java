package net.pool.station.core.domain.map.reverse;

import lombok.Builder;

import java.util.List;

public record ReverseGeo(
        List<Result> results
) {
    @Builder
    public record Result(
            String address
    ) {
        public static Result empty() {
            return Result.builder()
                    .address("")
                    .build();
        }
    }
}
