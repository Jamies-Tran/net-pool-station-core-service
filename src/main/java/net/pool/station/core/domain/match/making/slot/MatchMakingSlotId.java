package net.pool.station.core.domain.match.making.slot;

import lombok.Builder;
import lombok.With;

@Builder
public record MatchMakingSlotId(
        @With Long matchMakingId,
        Long timeSlotId
) {
}
