package net.pool.station.core.features.match.making.match.making.controller.models.schedule;

import java.time.LocalDate;

public record MatchScheduleResponse(
        MatchScheduleResponseId id,
        LocalDate date
) {
    public record MatchScheduleResponseId(
            Long scheduleId,
            Long matchMakingId
    ) {}
}
