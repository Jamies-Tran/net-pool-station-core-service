package net.pool.station.core.domain.match.making.resource;

import lombok.Builder;
import lombok.With;

@Builder
public record MatchMakingResourceId(
        @With Long matchMakingId,
        Long stationResourceId
) {
}
