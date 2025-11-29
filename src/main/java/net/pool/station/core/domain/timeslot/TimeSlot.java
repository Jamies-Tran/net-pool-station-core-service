package net.pool.station.core.domain.timeslot;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.bootstrap.enums.EPeriod;
import net.pool.station.core.domain.schedule.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public record TimeSlot(
    Long timeSlotId,
    Long scheduleId,
    LocalTime begin,
    LocalTime end,
    @With Boolean allowBooking,
    String periodCode,
    String periodName,
    String statusCode,
    String statusName
) {
    public TimeSlot {
        EPeriod period = EPeriod.getPeriod(begin);
        periodCode = period.getCode();
        periodName = period.getName();
    }

    public static List<TimeSlot> from(Long scheduleId, Schedule.TimeSlotConfig config) {
        List<TimeSlot> timeSlots = new ArrayList<>();
        LocalDateTime from = LocalDateTime.of(LocalDate.now(), config.from());
        LocalDateTime to = LocalDateTime.of(LocalDate.now(), config.to());

        while (from.isBefore(to) || from.isEqual(to)) {
            LocalDateTime end = from.plusSeconds(config.interval());
            if (end.toLocalDate().isAfter(from.toLocalDate())) {
                end = LocalDateTime.of(from.toLocalDate(), LocalTime.of(23, 59, 59));
                TimeSlot timeSlot = TimeSlot.builder()
                        .scheduleId(scheduleId)
                        .begin(from.toLocalTime())
                        .end(end.toLocalTime())
                        .build();
                timeSlots.add(timeSlot);
                break;
            }
            TimeSlot timeSlot = TimeSlot.builder()
                    .scheduleId(scheduleId)
                    .begin(from.toLocalTime())
                    .end(end.toLocalTime())
                    .build();
            timeSlots.add(timeSlot);
            from = end;
        }

        return timeSlots;
    }

    public static List<TimeSlot> from(List<Long> scheduleIds, Schedule.TimeSlotConfig config) {
        List<TimeSlot> timeSlots = new ArrayList<>();

        for (Long scheduleId : scheduleIds) {
            LocalDateTime from = LocalDateTime.of(LocalDate.now(), config.from());
            LocalDateTime to = LocalDateTime.of(LocalDate.now(), config.to());
            while (from.isBefore(to) || from.isEqual(to)) {
                LocalDateTime end = from.plusSeconds(config.interval());
                if (end.toLocalDate().isAfter(from.toLocalDate())) {
                    end = LocalDateTime.of(from.toLocalDate(), LocalTime.of(23, 59, 59));
                    TimeSlot timeSlot = TimeSlot.builder()
                            .scheduleId(scheduleId)
                            .begin(from.toLocalTime())
                            .end(end.toLocalTime())
                            .build();
                    timeSlots.add(timeSlot);
                    break;
                }
                TimeSlot timeSlot = TimeSlot.builder()
                        .scheduleId(scheduleId)
                        .begin(from.toLocalTime())
                        .end(end.toLocalTime())
                        .build();
                timeSlots.add(timeSlot);
                from = end;
            }
        }

        return timeSlots;
    }
}
