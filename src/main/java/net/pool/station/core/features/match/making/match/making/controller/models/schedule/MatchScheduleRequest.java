package net.pool.station.core.features.match.making.match.making.controller.models.schedule;

public record MatchScheduleRequest(
        MatchScheduleRequestId id
) {
    public record MatchScheduleRequestId(
            Long scheduleId
    ) {}
}
