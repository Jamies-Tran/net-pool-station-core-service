package net.pool.station.core.features.match.joining.controller.models;

public record MatchJoiningRegistrationRequest(
        Long matchMakingId,
        String message
) {
}
