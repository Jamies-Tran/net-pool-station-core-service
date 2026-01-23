package net.pool.station.core.domain.match.schedule;

import lombok.Builder;
import lombok.With;

@Builder
public record MatchScheduleId(
        @With
        Long matchMakingId,
        Long scheduleId
) {
}
