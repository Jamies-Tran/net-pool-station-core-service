package net.pool.station.core.features.station.space.schedule.slot.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.space.schedule.slot.controller.models.StationSpaceScheduleSlotListRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/station-space-slots")
public interface StationSpaceScheduleSlotsApi {
    @PostMapping("/{stationSpaceId}/{scheduleId}")
    MyValueResponse<?> save(@PathVariable Long stationSpaceId,
                            @PathVariable Long scheduleId,
                            @RequestBody @Valid StationSpaceScheduleSlotListRequest request);

    @DeleteMapping("/{stationSpaceId}/{timeSlotId}")
    MyValueResponse<?> delete(@PathVariable Long stationSpaceId,@PathVariable Long timeSlotId);

    @DeleteMapping("/all/{stationSpaceId}/{scheduleId}")
    MyValueResponse<?> deleteAll(@PathVariable Long stationSpaceId, @PathVariable Long scheduleId);
}
