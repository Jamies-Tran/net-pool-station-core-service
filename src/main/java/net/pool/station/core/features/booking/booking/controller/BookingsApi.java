package net.pool.station.core.features.booking.booking.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.booking.booking.controller.models.BookingRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/bookings")
public interface BookingsApi {
    @PostMapping
    @PreAuthorize("hasRole({'ROLE_PLAYER'})")
    MyValueResponse<?> save(@RequestBody @Valid BookingRequest bookingRequest);
}
