package net.pool.station.core.domain.match.participant;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.bootstrap.enums.EMatchMakingStatus;
import net.pool.station.core.bootstrap.enums.EMatchParticipantStatus;

import java.util.ArrayList;
import java.util.List;

@Builder
public record MatchParticipant(
        Long matchParticipantId,
        Long accountId,
        @With Long matchMakingId,
        String statusCode,
        String statusName
) {
    public static MatchParticipant ofEmpty() {
        return MatchParticipant.builder()
                .statusCode(EMatchParticipantStatus.EMPTY.getCode())
                .statusName(EMatchParticipantStatus.EMPTY.getName())
                .build();
    }

    public static List<MatchParticipant> ofEmptyList(int size) {
        List<MatchParticipant> list = new ArrayList<>();
        for (int i = 0; i <= size; i++) {
            list.add(ofEmpty());
        }

        return list;
    }
}
