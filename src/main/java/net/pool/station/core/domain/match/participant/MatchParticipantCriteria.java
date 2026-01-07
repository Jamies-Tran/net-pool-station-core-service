package net.pool.station.core.domain.match.participant;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.util.List;

@Builder
public record MatchParticipantCriteria(
        String search,
        Long matchMakingId,
        List<String> typeCodes,
        List<String> statusCodes
) {
    public MatchParticipantCriteria {
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        typeCodes = MyObjectUtils.defaultValue(typeCodes, new TypeReference<>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
    }
}
