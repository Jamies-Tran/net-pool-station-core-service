package net.pool.station.core.features.station.space.timeslot.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.space.timeslot.controller.models.StationSpaceTimeSlotListRequest;
import net.pool.station.core.features.station.space.timeslot.controller.models.StationSpaceTimeSlotRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/station-space-slots")
public interface StationSpaceTimeSlotsApi {
    @PostMapping
    @PreAuthorize("hasAnyRole({'ROLE_STATION_ADMIN', 'ROLE_STATION_OWNER'})")
    MyValueResponse<?> save(@RequestBody @Valid StationSpaceTimeSlotListRequest request);
}
