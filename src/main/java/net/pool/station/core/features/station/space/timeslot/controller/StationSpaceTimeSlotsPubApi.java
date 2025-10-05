package net.pool.station.core.features.station.space.timeslot.controller;

import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.features.station.space.timeslot.controller.models.StationSpaceTimeSlotResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/v1/pub/station-space-slots")
public interface StationSpaceTimeSlotsPubApi {
    @GetMapping
    MyPageResponse<StationSpaceTimeSlotResponse> findAll(
            @RequestParam(required = false, value = "scheduleId", defaultValue = "")
            Long scheduleId,

            @RequestParam(required = false, value = "stationId", defaultValue = "")
            Long stationId,

            @RequestParam(required = false, value = "spaceId", defaultValue = "")
            Long spaceId,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "createdAt")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,


            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );
}
