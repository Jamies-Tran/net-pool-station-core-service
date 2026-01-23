package net.pool.station.core.features.schedule.controller.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.time.LocalDate;

public record ScheduleCountListRequest(
        @NotNull(message = "Thông tin station không được bỏ trống")
        Long stationId,

        LocalDate dateFrom,

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
