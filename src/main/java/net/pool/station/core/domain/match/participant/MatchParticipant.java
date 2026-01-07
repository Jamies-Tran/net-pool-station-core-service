package net.pool.station.core.domain.match.participant;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.bootstrap.enums.EMatchMakingStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantType;
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
        String statusCode,
        String statusName,
        @With Account account
) {
    public static MatchParticipant ofEmpty() {
        return MatchParticipant.builder()
                .statusCode(EMatchParticipantStatus.EMPTY.getCode())
                .statusName(EMatchParticipantStatus.EMPTY.getName())
                .build();
    }

    public static MatchParticipant ofHost(Long hostId) {
        return MatchParticipant.builder()
                .accountId(hostId)
                .typeCode(EMatchParticipantType.HOST.getCode())
                .typeName(EMatchParticipantType.HOST.getName())
                .statusCode(EMatchParticipantStatus.FILLED.getCode())
                .statusName(EMatchParticipantStatus.FILLED.getName())
                .build();

    }

    public static List<MatchParticipant> ofEmptyList(int size, long hostId) {
        List<MatchParticipant> list = new ArrayList<>(List.of(ofHost(hostId)));
        for (int i = 0; i <= size; i++) {
            list.add(ofEmpty());
        }

        return list;
    }
}
