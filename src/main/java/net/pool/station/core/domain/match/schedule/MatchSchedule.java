package net.pool.station.core.domain.match.schedule;

import lombok.Builder;
import lombok.With;

import java.time.LocalDate;

@Builder
public record MatchSchedule(
        @With MatchScheduleId id,
        LocalDate date
) {
}
