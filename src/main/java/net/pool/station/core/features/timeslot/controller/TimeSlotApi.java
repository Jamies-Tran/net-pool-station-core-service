package net.pool.station.core.features.timeslot.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/time-slots/{timeSlotId}")
public interface TimeSlotApi {
    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_STATION_OWNER')")
    MyValueResponse<?> delete(@PathVariable Long timeSlotId);
}
