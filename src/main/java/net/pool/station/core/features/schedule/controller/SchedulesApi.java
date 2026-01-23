package net.pool.station.core.features.schedule.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.schedule.controller.models.ScheduleListRequest;
import net.pool.station.core.features.schedule.controller.models.ScheduleRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/schedules")
public interface SchedulesApi {
    @PostMapping
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> save(@RequestBody @Valid ScheduleRequest request);

    @PostMapping("/list")
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> save(@RequestBody @Valid ScheduleListRequest request);
}
