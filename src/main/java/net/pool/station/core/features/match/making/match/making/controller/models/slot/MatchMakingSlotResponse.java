package net.pool.station.core.features.match.making.match.making.controller.models.slot;

import java.time.LocalTime;

public record MatchMakingSlotResponse(
        MatchMakingSlotResponseId id,
        LocalTime begin,
        LocalTime end
) {
    public record MatchMakingSlotResponseId(
            Long matchMakingId,
            Long timeSlotId
    ) {}
}
