package net.pool.station.core.features.booking.booking.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.booking.BookingUseCase;
import net.pool.station.core.features.booking.booking.controller.models.BookingRequest;
import net.pool.station.core.features.booking.booking.controller.models.BookingRequestMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingsController implements BookingsApi {
    BookingUseCase bookingUseCase;

    BookingRequestMapper requestMapper;

    @Override
    public MyValueResponse<?> save(BookingRequest bookingRequest) {
        bookingUseCase.save(requestMapper.toDto(bookingRequest));

        return MyValueResponse.successNoData();
    }
}
