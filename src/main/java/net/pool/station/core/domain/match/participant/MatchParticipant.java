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
        Integer paidDeposit,
        String statusCode,
        String statusName,
        @With Account account
) {
    public static MatchParticipant ofEmpty() {

        return MatchParticipant.builder()
                .shareAmount(0)
                .statusCode(EMatchParticipantStatus.EMPTY.getCode())
                .statusName(EMatchParticipantStatus.EMPTY.getName())
                .build();
    }

    public static MatchParticipant ofHost(Long hostId, int totalPrice, int paidDeposit) {
        return MatchParticipant.builder()
                .accountId(hostId)
                .shareAmount(totalPrice)
                .paidDeposit(paidDeposit)
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
                                                     int paidDeposit,
                                                     long hostId
    ) {
        List<MatchParticipant> list = new ArrayList<>(List
                .of(ofHost(hostId, totalPrice, paidDeposit)));
        for (int i = 0; i <= size; i++) {
            list.add(ofEmpty());
        }

        return list;
    }
}
