package net.pool.station.core.features.booking.booking.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.booking.booking.controller.models.BookingRequest;
import net.pool.station.core.features.booking.booking.controller.models.BookingResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/v1/api/bookings")
public interface BookingsApi {
    @PostMapping
    @PreAuthorize("hasRole({'ROLE_PLAYER'})")
    MyValueResponse<?> save(@RequestBody @Valid BookingRequest bookingRequest);

    @GetMapping
    MyPageResponse<BookingResponse> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "")
            String search,

            @RequestParam(required = false, value = "accountId", defaultValue = "")
            String accountId,

            @RequestParam(required = false, value = "stationId", defaultValue = "")
            Long stationId,

            @RequestParam(required = false, value = "dateRange", defaultValue = "")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            List<LocalDate> dateRange,

            @RequestParam(required = false, value = "typeCodes", defaultValue = "")
            List<String> typeCodes,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "startAt_desc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );
}
