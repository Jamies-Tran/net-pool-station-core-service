package net.pool.station.core.domain.schedule;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.domain.timeslot.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public record Schedule(
        Long scheduleId,
        Long stationId,
        LocalDate date,
        String statusCode,
        String statusName,
        @With List<TimeSlot> timeSlots,
        TimeSlotConfig timeSlotConfig
) {
    @Builder
    public record TimeSlotConfig(
            LocalTime from,
            LocalTime to,
            Integer interval
    ) {}

    public static List<Schedule> from(Long stationId, LocalDate from, LocalDate to) {
        List<Schedule> schedules = new ArrayList<>();
        while (from.isBefore(to) || from.isEqual(to)) {
            schedules.add(Schedule.builder()
                    .stationId(stationId)
                    .date(from)
                    .build());
            from = from.plusDays(1);
        }

        return schedules;
    }
}
