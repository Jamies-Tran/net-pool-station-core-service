package net.pool.station.core.features.timeslot.controller.models;

import java.time.LocalTime;

public record TimeSlotResponse(
        Long timeSlotId,
        Long scheduleId,
        LocalTime begin,
        LocalTime end,
        String periodCode,
        String periodName,
        String statusCode,
        String statusName
) {
}
