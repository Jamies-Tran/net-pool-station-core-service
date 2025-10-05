package net.pool.station.core.features.schedule.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.schedule.controller.models.ScheduleRequest;
import net.pool.station.core.features.schedule.controller.models.ScheduleResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/schedules/{scheduleId}")
public interface ScheduleApi {
    @GetMapping
    @PreAuthorize("hasAnyRole({'ROLE_STATION_OWNER', 'ROLE_STATION_ADMIN'})")
    MyValueResponse<ScheduleResponse> findById(@PathVariable Long scheduleId);

    @PutMapping
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> update(@PathVariable Long scheduleId, @RequestBody @Valid ScheduleRequest request);

    @PatchMapping("/enable")
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> enable(@PathVariable Long scheduleId);

    @PatchMapping("/disable")
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> disable(@PathVariable Long scheduleId);

    @DeleteMapping
    @PreAuthorize("hasRole({'ROLE_STATION_OWNER'})")
    MyValueResponse<?> delete(@PathVariable Long scheduleId);
}
