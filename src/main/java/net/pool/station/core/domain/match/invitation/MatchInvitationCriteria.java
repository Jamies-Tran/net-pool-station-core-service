package net.pool.station.core.domain.match.invitation;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MatchInvitationCriteria(
        Long accountId,
        Long matchMakingId,
        List<String> statusCodes,
        List<LocalDateTime> timeRange
) {
    public MatchInvitationCriteria {
        accountId = MyObjectUtils.defaultValue(accountId, new TypeReference<>() {});
        matchMakingId = MyObjectUtils.defaultValue(matchMakingId, new TypeReference<>() {});
        timeRange = MyObjectUtils.defaultValue(timeRange, new TypeReference<>() {});
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
    }

    public LocalDateTime from() {
        if (MyObjectUtils.isEmpty(timeRange)) {
            return null;
        }

        return timeRange.getFirst();
    }

    public LocalDateTime to() {
        if (MyObjectUtils.isEmpty(timeRange)) {
            return null;
        }
        if (MyObjectUtils.isEquals(timeRange.size(), 1)) {
            return timeRange.getFirst();
        }
        return timeRange.get(1);
    }
}
