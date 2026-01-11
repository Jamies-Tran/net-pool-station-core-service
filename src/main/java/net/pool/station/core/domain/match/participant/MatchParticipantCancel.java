package net.pool.station.core.domain.match.participant;

import lombok.Builder;

@Builder
public record MatchParticipantCancel(
        Long matchMakingId,
        Boolean isCancel
) {
}
