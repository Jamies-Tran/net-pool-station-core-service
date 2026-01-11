package net.pool.station.core.domain.match.participant;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.bootstrap.enums.EMatchMakingStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantReadyStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantType;
import net.pool.station.core.bootstrap.utils.MyPaymentUtils;
import net.pool.station.core.domain.account.Account;

import java.util.ArrayList;
import java.util.List;

@Builder
public record MatchParticipant(
        Long matchParticipantId,
        Long accountId,
        @With Long matchMakingId,
        String typeCode,
        String typeName,
        String paymentMethodCode,
        String paymentMethodName,
        String readyStatusCode,
        String readyStatusName,
        Integer shareAmount,
        String statusCode,
        String statusName,
        @With Account account
) {
    public static MatchParticipant ofEmpty(int shareAmount) {

        return MatchParticipant.builder()
                .shareAmount(shareAmount)
                .statusCode(EMatchParticipantStatus.EMPTY.getCode())
                .statusName(EMatchParticipantStatus.EMPTY.getName())
                .build();
    }

    public static MatchParticipant ofHost(Long hostId, int totalPrice, int shareAmount, int numberOfHoldingDay) {
        int paidDeposit = MyPaymentUtils.calculateDeposit(totalPrice, numberOfHoldingDay);
        return MatchParticipant.builder()
                .accountId(hostId)
                .shareAmount(shareAmount - paidDeposit)
                .typeCode(EMatchParticipantType.HOST.getCode())
                .typeName(EMatchParticipantType.HOST.getName())
                .readyStatusCode(EMatchParticipantReadyStatus.READY.getCode())
                .readyStatusName(EMatchParticipantReadyStatus.READY.getName())
                .statusCode(EMatchParticipantStatus.FILLED.getCode())
                .statusName(EMatchParticipantStatus.FILLED.getName())
                .build();

    }

    public static List<MatchParticipant> ofEmptyList(int size,
                                                     int totalPrice,
                                                     int numberOfHoldingDay,
                                                     long hostId
    ) {
        int shareAmount = totalPrice / size;
        List<MatchParticipant> list = new ArrayList<>(List
                .of(ofHost(hostId, totalPrice, shareAmount, numberOfHoldingDay)));
        for (int i = 0; i <= size; i++) {
            list.add(ofEmpty(shareAmount));
        }

        return list;
    }
}
