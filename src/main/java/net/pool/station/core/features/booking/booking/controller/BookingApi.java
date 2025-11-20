package net.pool.station.core.features.booking.booking.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.booking.booking.controller.models.BookingCancelRequest;
import net.pool.station.core.features.booking.booking.controller.models.BookingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/bookings/{bookingId}")
public interface BookingApi {
    @GetMapping
    MyValueResponse<BookingResponse> findById(@PathVariable Long bookingId);

    @PatchMapping("/cancel")
    MyValueResponse<?> cancel(@PathVariable Long bookingId, @RequestBody @Valid BookingCancelRequest request);
}
