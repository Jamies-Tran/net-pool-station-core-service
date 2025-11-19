package net.pool.station.core.features.booking.booking.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.booking.booking.controller.models.BookingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/bookings/{bookingId}")
public interface BookingApi {
    @GetMapping
    MyValueResponse<BookingResponse> findById(@PathVariable Long bookingId);
}
