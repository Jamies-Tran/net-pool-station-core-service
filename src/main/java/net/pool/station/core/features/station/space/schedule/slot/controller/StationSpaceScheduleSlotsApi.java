package net.pool.station.core.features.station.space.schedule.slot.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.space.schedule.slot.controller.models.StationSpaceScheduleSlotListRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/station-space-slots")
public interface StationSpaceScheduleSlotsApi {
    @PostMapping("/{stationSpaceScheduleId}")
    MyValueResponse<?> save(@PathVariable Long stationSpaceScheduleId, StationSpaceScheduleSlotListRequest request);
}
