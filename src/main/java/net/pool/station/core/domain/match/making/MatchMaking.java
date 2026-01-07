package net.pool.station.core.domain.match.making;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.bootstrap.enums.EMatchMakingStatus;
import net.pool.station.core.bootstrap.enums.EResourceType;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.match.making.resource.MatchMakingResource;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlot;
import net.pool.station.core.domain.match.participant.MatchParticipant;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public record MatchMaking(
        Long matchMakingId,
        Long stationId,
        Long gameId,
        Long scheduleId,
        @With Long walletId,
        String matchMakingCode,
        Integer numberOfHoldingDay,
        Integer limitParticipant,
        @With LocalDate startAt,
        @With LocalDate expiredAt,
        String resourceTypeCode,
        String resourceTypeName,
        String typeCode,
        String typeName,
        String paymentMethodCode,
        String paymentMethodName,
        String statusCode,
        String statusName,
        Integer totalPrice,
        String createdBy,
        @With Boolean allowJoin,
        @With List<MatchMakingSlot> slots,
        @With List<MatchMakingResource> resources,
        @With List<MatchParticipant> participants
) {
    public MatchMaking {
        if (MyObjectUtils.isEmpty(matchMakingCode)) {
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyyyy"));
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
            String random = RandomStringUtils.randomAlphanumeric(6);
            matchMakingCode = "MATCH_%s_%s_%s".formatted(date, time, random);
        }

        if (!CollectionUtils.isEmpty(slots) && !CollectionUtils.isEmpty(resources)) {
            totalPrice = resources.stream()
                    .mapToInt(r -> Optional.ofNullable(r.price()).orElse(0)
                            * slots.size())
                    .sum();
        }

        if (MyObjectUtils.isNotEmpty(resources)) {
            limitParticipant = Integer.parseInt(EResourceType.valueOf(resourceTypeCode).getType())
                    * resources.size();
        }
    }
}
