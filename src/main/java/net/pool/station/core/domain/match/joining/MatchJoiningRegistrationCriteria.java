package net.pool.station.core.domain.match.joining;

import com.fasterxml.jackson.core.type.TypeReference;
import net.pool.station.core.bootstrap.utils.MyDateTimeUtils;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.time.LocalDateTime;
import java.util.List;

public record MatchJoiningRegistrationCriteria(
        Long matchMakingId,
        String createdBy,
        List<String> statusCodes,
        List<LocalDateTime> timeRange
) {
    public MatchJoiningRegistrationCriteria {
        timeRange = MyDateTimeUtils.defaultTimeRange(timeRange);
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
        createdBy = MyObjectUtils.defaultValue(createdBy, new TypeReference<>() {});
        matchMakingId = MyObjectUtils.defaultValue(matchMakingId, new TypeReference<>() {});
    }
}
