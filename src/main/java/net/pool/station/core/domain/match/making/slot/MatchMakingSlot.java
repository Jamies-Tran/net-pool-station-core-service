package net.pool.station.core.domain.match.making.slot;

import lombok.Builder;
import lombok.With;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@With
public record MatchMakingSlot(
        MatchMakingSlotId id,
        LocalTime begin,
        LocalTime end,
        LocalDate startAt,
        LocalDate expiredAt,
        String matchMakingStatusCode
) {
}
