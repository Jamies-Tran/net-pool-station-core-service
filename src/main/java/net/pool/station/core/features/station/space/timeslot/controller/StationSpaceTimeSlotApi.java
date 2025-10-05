package net.pool.station.core.features.station.space.timeslot.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/api/station-space-slots")
public interface StationSpaceTimeSlotApi {
    @PatchMapping("/enable")
    MyValueResponse<?> enable(
            @RequestParam
            Long stationId,

            @RequestParam
            Long spaceId,

            @RequestParam
            Long timeSlotId
    );

    @PatchMapping("/disable")
    MyValueResponse<?> disable(
            @RequestParam
            Long stationId,

            @RequestParam
            Long spaceId,

            @RequestParam
            Long timeSlotId
    );

    @DeleteMapping
    MyValueResponse<?> delete(
            @RequestParam
            Long stationId,

            @RequestParam
            Long spaceId,

            @RequestParam
            Long timeSlotId
    );
}
