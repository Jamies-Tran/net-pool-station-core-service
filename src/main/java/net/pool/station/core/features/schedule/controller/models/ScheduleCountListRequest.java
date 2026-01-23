package net.pool.station.core.features.schedule.controller.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record ScheduleCountListRequest(
        @NotNull(message = "Thông tin station không được bỏ trống")
        Long stationId,

        LocalDate dateFrom,

        LocalTime begin,

        LocalTime end,

        List<Long> stationResourceId,

        @Max(value = 3, message = "Giá trị đếm cao nhất là 3 ngày")
        Integer dateCount
) {
    public ScheduleCountListRequest {
        if (MyObjectUtils.isEmpty(dateFrom)) {
            dateFrom = LocalDate.now();
        }

        if (MyObjectUtils.isEmpty(dateCount)) {
            dateCount = 3;
        }
    }
}
