package net.pool.station.core.domain.match.making;

import lombok.With;
import net.pool.station.core.bootstrap.enums.EResourceType;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.match.making.resource.MatchMakingResource;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlot;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import net.pool.station.core.domain.match.schedule.MatchSchedule;
import net.pool.station.core.domain.transaction.Transaction;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public record MatchMaking(
        Long matchMakingId,
        Long stationId,
        Long gameId,
        @With Long ownerWalletId,
        @With Long playerWalletId,
        String matchMakingCode,
        Integer numberOfHoldingDay,
        Integer limitParticipant,
        @With LocalDate startAt,
        @With LocalDate expiredAt,
        LocalDate processAt,
        LocalDateTime playAt,
        String resourceTypeCode,
        String resourceTypeName,
        String typeCode,
        String typeName,
        String paymentMethodCode,
        String paymentMethodName,
        String statusCode,
        String statusName,
        @With Integer totalPrice,
        LocalDateTime paidDepositAt,
        String createdBy,
        LocalDateTime createdAt,
        @With Boolean allowJoin,
        @With Boolean allowView,
        @With List<MatchMakingSlot> slots,
        @With List<MatchMakingResource> resources,
        @With List<MatchParticipant> participants,
        @With List<MatchSchedule> schedules,
        @With List<Transaction> transactions
) {
    public MatchMaking {
        if (MyObjectUtils.isEmpty(matchMakingCode)) {
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
            String random = RandomStringUtils.randomAlphanumeric(6);
            matchMakingCode = "MATCH_%s_%s_%s".formatted(date, time, random);
        }

        if (MyObjectUtils.isNotEmpty(schedules)) {
            numberOfHoldingDay = schedules.size();
        }


        if (MyObjectUtils.isNotEmpty(resources)) {
            limitParticipant = Integer.parseInt(EResourceType.valueOf(resourceTypeCode).getType())
                    * resources.size();
        }
    }
}
