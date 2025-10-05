package net.pool.station.core.features.schedule.controller.models;

import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EIntervalType;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

public record ScheduleListRequest(
        Long stationId,
        LocalDate dateFrom,
        LocalDate dateTo,
        TimeSlotConfigRequest timeSlotConfig
) {
    public record TimeSlotConfigRequest (
            LocalTime from,
            LocalTime to,
            String intervalTypeCode,
            Integer interval
    ) {
        public TimeSlotConfigRequest {
            EIntervalType intervalType = Stream.of(EIntervalType.values())
                    .filter(i -> MyObjectUtils.isEquals(intervalTypeCode, i.getCode()))
                    .findAny()
                    .orElseThrow(() -> new MyResourceNotFoundException("Khoản thời gian không hợp lệ"));
            interval = intervalType.getValue();
        }
    }
}
