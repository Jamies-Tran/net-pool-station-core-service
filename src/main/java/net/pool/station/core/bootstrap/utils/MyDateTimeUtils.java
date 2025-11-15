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
    private static Integer dateTimeDistance;

    private static Integer dateDistance;

    private static String unit;

    @Value("${environment.dateTime.unit:DAY}")
    public void setUnit(String unit) {
        MyDateTimeUtils.unit = unit;
    }



    @Value("${environment.dateTime.distance:1}")
    public void setDateTimeDistance(Integer dateTimeDistance) {
        MyDateTimeUtils.dateTimeDistance = dateTimeDistance;
    }

    @Value("${environment.date.distance:7}")
    public void setDateDistance(Integer dateDistance) {
        MyDateTimeUtils.dateDistance = dateDistance;
    }

    public static List<LocalDateTime> defaultTimeRange(List<LocalDateTime> timeRange) {
        if (timeRange.size() == 1) {
            LocalDateTime start = timeRange.getFirst();
            LocalDateTime end = adjustDateTime(start, false);
            return List.of(start, end);
        }

        if (timeRange.isEmpty()) {
            LocalDateTime end = LocalDateTime.now();
            LocalDateTime start = adjustDateTime(end, false);
            return List.of(start, end);
        }

        if (timeRange.size() > 2) {
            return Stream.of(timeRange.get(0), timeRange.get(1))
                    .sorted(Comparator.reverseOrder())
                    .toList();
        }

        return timeRange;
    }

    public static List<LocalDate> defaultDateRange(List<LocalDate> dateRange) {
        if (dateRange.isEmpty()) {
            return List.of(LocalDate.now(), adjustDate(LocalDate.now(), true));
        }

        if (dateRange.size() == 1) {
            return List.of(dateRange.getFirst(), adjustDate(dateRange.getFirst(), true));
        }

        return Stream.of(dateRange.get(0), dateRange.get(1))
                .sorted()
                .toList();

    }

    private static LocalDate adjustDate(LocalDate start, Boolean isForward) {
        return Objects.nonNull(isForward) && isForward
                ? start.plusDays(dateDistance) : start.minusDays(dateDistance);
    }

    private static LocalDateTime adjustDateTime(LocalDateTime start, Boolean isForward) {
        switch (unit) {
            case "DAY" -> {
                return Objects.nonNull(isForward) && isForward
                        ? start.plusDays(dateTimeDistance) : start.minusDays(dateTimeDistance);
            }
            case "HOUR" -> {
                return Objects.nonNull(isForward) && isForward
                        ? start.plusHours(dateTimeDistance) : start.minusHours(dateTimeDistance);
            }
            case "MINUTE" -> {
                return Objects.nonNull(isForward) && isForward
                        ? start.plusMinutes(dateTimeDistance) : start.minusMinutes(dateTimeDistance);
            }
            default -> {
                return start;
            }
        }
    }
}
