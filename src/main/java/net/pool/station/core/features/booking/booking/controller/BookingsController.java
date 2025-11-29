package net.pool.station.core.features.booking.booking.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.bootstrap.rest.response.MySorter;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.booking.BookingCriteria;
import net.pool.station.core.domain.booking.BookingUseCase;
import net.pool.station.core.features.booking.booking.controller.models.BookingRequest;
import net.pool.station.core.features.booking.booking.controller.models.BookingRequestMapper;
import net.pool.station.core.features.booking.booking.controller.models.BookingResponse;
import net.pool.station.core.features.booking.booking.controller.models.BookingResponseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingsController implements BookingsApi {
    BookingUseCase bookingUseCase;

    BookingRequestMapper requestMapper;

    BookingResponseMapper responseMapper;

    @Override
    public MyValueResponse<?> save(BookingRequest bookingRequest) {
        bookingUseCase.save(requestMapper.toDto(bookingRequest));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyPageResponse<BookingResponse> findAll(
            String search,
            Long accountId,
            List<LocalDate> dateRange,
            List<String> typeCodes,
            List<String> statusCodes,
            String sorter,
            Integer current,
            Integer pageSize
    ) {
        BookingCriteria criteria = BookingCriteria.of(search, accountId, dateRange, typeCodes, statusCodes);
        PageRequest pageRequest = PageRequest.of(current, pageSize, MySorter.of(sorter));
        Page<BookingResponse> responses = bookingUseCase.findAll(criteria, pageRequest)
                .map(responseMapper::toModel);

        return MyPageResponse.success(responses);
    }
}
