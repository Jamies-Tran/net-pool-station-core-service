package net.pool.station.core.bootstrap.utils;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class MyDateTimeUtils {
    @Value("${environment.dateTime.distance:1}")
    private static Integer distance;

    @Value("${environment.dateTime.unit:DAY}")
    private static String unit;

    public static List<LocalDateTime> defaultTimeRange(List<LocalDateTime> timeRange) {
        if (timeRange.size() == 1) {
            LocalDateTime start = timeRange.getFirst();
            LocalDateTime end = adjust(start, false);
            return List.of(start, end);
        }

        if (timeRange.isEmpty()) {
            LocalDateTime start = LocalDateTime.now();
            LocalDateTime end = adjust(start, false);
            return List.of(start, end);
        }

        if (timeRange.size() > 2) {
            return Stream.of(timeRange.get(0), timeRange.get(1))
                    .sorted(Comparator.reverseOrder())
                    .toList();
        }

        return timeRange;
    }

    private static LocalDateTime adjust(LocalDateTime start, Boolean isForward) {
        switch (unit) {
            case "DAY" -> {
                return Objects.nonNull(isForward) && isForward
                        ? start.plusDays(distance) : start.minusDays(distance);
            }
            case "HOUR" -> {
                return Objects.nonNull(isForward) && isForward
                        ? start.plusHours(distance) : start.minusHours(distance);
            }
            case "MINUTE" -> {
                return Objects.nonNull(isForward) && isForward
                        ? start.plusMinutes(distance) : start.minusMinutes(distance);
            }
            default -> {
                return start;
            }
        }
    }
}
