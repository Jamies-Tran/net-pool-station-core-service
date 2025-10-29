package net.pool.station.core.features.station.space.timeslot.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/station-space-slots/{stationSpaceSlotId}")
public interface StationSpaceTimeSlotApi {
    @PatchMapping("/enable")
    MyValueResponse<?> enable(@PathVariable Long stationSpaceSlotId);

    @PatchMapping("/disable")
    MyValueResponse<?> disable(@PathVariable Long stationSpaceSlotId);

    @DeleteMapping
    MyValueResponse<?> delete(@PathVariable Long stationSpaceSlotId);
}
