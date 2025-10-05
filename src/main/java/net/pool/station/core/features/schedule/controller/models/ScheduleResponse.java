package net.pool.station.core.features.schedule.controller.models;

import net.pool.station.core.features.timeslot.controller.models.TimeSlotResponse;

import java.time.LocalDate;
import java.util.List;

public record ScheduleResponse(
        Long scheduleId,
        Long stationId,
        LocalDate date,
        String statusCode,
        String statusName,
        List<TimeSlotResponse> timeSlots
) {
}
