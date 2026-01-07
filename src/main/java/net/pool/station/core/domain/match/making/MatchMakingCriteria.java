package net.pool.station.core.domain.match.making;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Builder
public record MatchMakingCriteria(
        String search,
        List<LocalDate> timeRangeStartAt,
        List<String> statusCodes
) {
    public MatchMakingCriteria {
        search = MyObjectUtils.defaultValue(search, new TypeReference<>() {});
        timeRangeStartAt = defaultTimeRangeCustom(timeRangeStartAt);
        statusCodes = MyObjectUtils.defaultValue(statusCodes, new TypeReference<>() {});
    }

    private List<LocalDate> defaultTimeRangeCustom(List<LocalDate> timeRangeStartAt) {
        if (timeRangeStartAt.size() == 1) {
            LocalDate start = timeRangeStartAt.getFirst();
            LocalDate end = start.plusDays(1);
            return List.of(start, end);
        }

        if (timeRangeStartAt.isEmpty()) {
            LocalDate end = LocalDate.now();
            LocalDate start = end.minusDays(1);
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
