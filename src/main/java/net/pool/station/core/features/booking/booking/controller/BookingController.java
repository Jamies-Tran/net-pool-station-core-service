package net.pool.station.core.features.booking.booking.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.BookingUseCase;
import net.pool.station.core.features.booking.booking.controller.models.BookingResponse;
import net.pool.station.core.features.booking.booking.controller.models.BookingResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController implements BookingApi {
    BookingUseCase bookingUseCase;

    BookingResponseMapper responseMapper;

    @Override
    public MyValueResponse<BookingResponse> findById(Long bookingId) {
        BookingResponse response = bookingUseCase.findById(DomainKey.of(bookingId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }
}
