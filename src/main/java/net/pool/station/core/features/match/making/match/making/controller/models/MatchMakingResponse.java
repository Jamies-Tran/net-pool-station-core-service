package net.pool.station.core.features.match.making.match.making.controller.models;

import net.pool.station.core.features.game.controller.models.GameResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.participant.MatchParticipantResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.resource.MatchMakingResourceResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.schedule.MatchScheduleResponse;
import net.pool.station.core.features.match.making.match.making.controller.models.slot.MatchMakingSlotResponse;
import net.pool.station.core.features.transaction.controller.models.TransactionResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record MatchMakingResponse (
        Long matchMakingId,
        Long stationId,
        Long gameId,
        String matchMakingCode,
        Integer limitParticipant,
        Integer totalPrice,
        Integer totalDeposit,
        LocalDate startAt,
        LocalDate expiredAt,
        LocalDateTime playAt,
        String statusCode,
        String statusName,
        Boolean allowJoin,
        Boolean allowView,
        GameResponse game,
        List<MatchMakingSlotResponse> slots,
        List<MatchMakingResourceResponse> resources,
        List<MatchParticipantResponse> participants,
        List<MatchScheduleResponse> schedules,
        List<TransactionResponse> transactions
) {
}
