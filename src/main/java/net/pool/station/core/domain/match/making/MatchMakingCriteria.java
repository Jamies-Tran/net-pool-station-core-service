package net.pool.station.core.domain.match.making;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.bootstrap.utils.MyDateTimeUtils;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.login.info.LoginInfo;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Builder
public record MatchMakingCriteria(
        String search,
        List<LocalDateTime> timeRangeStartAt,
        List<String> statusCodes
) {
    public MatchMakingCriteria {
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        timeRangeStartAt = defaultTimeRangeCustom(timeRangeStartAt);
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
    }

    private List<LocalDateTime> defaultTimeRangeCustom(List<LocalDateTime> timeRangeStartAt) {
        if (timeRangeStartAt.size() == 1) {
            LocalDateTime start = timeRangeStartAt.getFirst();
            LocalDateTime end = start.plusDays(1);
            return List.of(start, end);
        }

        if (timeRangeStartAt.isEmpty()) {
            LocalDateTime end = LocalDateTime.now();
            LocalDateTime start = end.minusDays(1);
            return List.of(start, end);
        }

        if (timeRangeStartAt.size() > 2) {
            return Stream.of(timeRangeStartAt.get(0), timeRangeStartAt.get(1))
                    .sorted(Comparator.reverseOrder())
                    .toList();
        }

        return timeRangeStartAt;
    }
}
