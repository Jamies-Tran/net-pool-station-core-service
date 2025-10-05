package net.pool.station.core.features.schedule.controller;

import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.features.schedule.controller.models.ScheduleResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/v1/pub/schedules")
public interface SchedulesPubApi {
    @GetMapping
    MyPageResponse<ScheduleResponse> findAll(
            @RequestParam(value = "stationId")
            Long stationId,

            @RequestParam(required = false, value = "dateRange", defaultValue = "")
            List<LocalDate> dateRange,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "date_desc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );
}
