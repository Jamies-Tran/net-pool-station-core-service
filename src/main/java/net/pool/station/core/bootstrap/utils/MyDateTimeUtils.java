package net.pool.station.core.bootstrap.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Configuration
public class MyDateTimeUtils {
    private static Integer distance;

    private static String unit;

    @Value("${environment.dateTime.unit:DAY}")
    public void setUnit(String unit) {
        MyDateTimeUtils.unit = unit;
    }

    @Value("${environment.dateTime.distance:1}")
    public void setDistance(Integer distance) {
        MyDateTimeUtils.distance = distance;
    }

    public static List<LocalDateTime> defaultTimeRange(List<LocalDateTime> timeRange) {
        if (timeRange.size() == 1) {
            LocalDateTime start = timeRange.getFirst();
            LocalDateTime end = adjust(start, false);
            return List.of(start, end);
        }

        if (timeRange.isEmpty()) {
            LocalDateTime end = LocalDateTime.now();
            LocalDateTime start = adjust(end, false);
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

    public static List<LocalDate> defaultDateRange(List<LocalDate> dateRange) {
        if (dateRange.size() == 1 || dateRange.isEmpty()) {
            return List.of();
        }

        if (dateRange.size() > 2) {
            return Stream.of(dateRange.get(0), dateRange.get(1))
                    .sorted(Comparator.reverseOrder())
                    .toList();
        }

        return dateRange;
    }
}
