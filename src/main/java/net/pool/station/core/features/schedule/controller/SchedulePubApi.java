package net.pool.station.core.features.schedule.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.schedule.controller.models.ScheduleResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/pub/schedules/{scheduleId}")
public interface SchedulePubApi {
    @GetMapping
    MyValueResponse<ScheduleResponse> findById(@PathVariable Long scheduleId);

    @GetMapping("/{stationResourceId}")
    MyValueResponse<ScheduleResponse> findById(@PathVariable Long scheduleId, @PathVariable Long stationResourceId);
}
