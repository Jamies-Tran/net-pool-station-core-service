package net.pool.station.core.domain.match.making.resource;

import lombok.Builder;
import lombok.With;

@Builder
@With
public record MatchMakingResource(
    MatchMakingResourceId id,
    String typeCode,
    String typeName,
    Integer price
) {
}
