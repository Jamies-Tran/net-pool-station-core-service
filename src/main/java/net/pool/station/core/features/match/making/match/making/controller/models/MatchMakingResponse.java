package net.pool.station.core.features.match.making.match.making.controller.models;

import java.time.LocalDateTime;

public record MatchMakingResponse(
        Long matchMakingId,
        Long stationId,
        String matchMakingCode,
        Integer limitParticipant,
        LocalDateTime startAt,
        LocalDateTime endAt,
        String statusCode,
        String statusName
) {
}
