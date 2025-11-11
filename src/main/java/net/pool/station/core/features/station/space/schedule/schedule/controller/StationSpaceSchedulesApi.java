package net.pool.station.core.features.station.space.schedule.schedule.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.space.schedule.schedule.controller.models.StationSpaceScheduleListRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/station-space-schedules")
public interface StationSpaceSchedulesApi {
    @PostMapping("/{scheduleId}")
    MyValueResponse<?> save(@PathVariable Long scheduleId, @RequestBody @Valid StationSpaceScheduleListRequest request);
}
