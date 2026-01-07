package net.pool.station.core.features.match.making.match.making.controller.models;

import net.pool.station.core.features.match.making.match.making.controller.models.participant.MatchParticipantResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.resource.MatchMakingResourceResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.slot.MatchMakingSlotResponse;

import java.time.LocalDate;
import java.util.List;

public record MatchMakingResponse (
        Long matchMakingId,
        Long stationId,
        String matchMakingCode,
        Integer limitParticipant,
        LocalDate startAt,
        LocalDate expiredAt,
        String statusCode,
        String statusName,
        Boolean allowJoin,
        List<MatchMakingSlotResponse> slots,
        List<MatchMakingResourceResponse> resources,
        List<MatchParticipantResponse> participants
) {
}
