package net.pool.station.core.features.booking.booking.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.BookingUseCase;
import net.pool.station.core.features.booking.booking.controller.models.BookingCancelRequest;
import net.pool.station.core.features.booking.booking.controller.models.BookingRequest;
import net.pool.station.core.features.booking.booking.controller.models.BookingRequestMapper;
import net.pool.station.core.features.booking.booking.controller.models.BookingResponse;
import net.pool.station.core.features.booking.booking.controller.models.BookingResponseMapper;
import net.pool.station.core.features.payment.controller.payment.models.PaymentResponse;
import net.pool.station.core.features.payment.controller.payment.models.PaymentResponseMapper;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookingController implements BookingApi {
    BookingUseCase bookingUseCase;

    BookingRequestMapper requestMapper;

    BookingResponseMapper responseMapper;

    PaymentResponseMapper paymentResponseMapper;

    @Override
    public MyValueResponse<BookingResponse> findById(Long bookingId) {
        BookingResponse response = bookingUseCase.findById(DomainKey.of(bookingId))
                .map(responseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }

    @Override
    public MyValueResponse<?> update(Long bookingId, BookingRequest request) {
        bookingUseCase.update(DomainKey.of(bookingId), requestMapper.toDto(request));

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> cancel(Long bookingId, BookingCancelRequest request) {
        bookingUseCase.cancel(DomainKey.of(bookingId), request.cancelReason());

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<PaymentResponse> generatePayment(Long bookingId) {
        PaymentResponse response = bookingUseCase.generatePayment(DomainKey.of(bookingId))
                .map(paymentResponseMapper::toModel)
                .orElseThrow(MyResourceNotFoundException::new);

        return MyValueResponse.success(response);
    }

    @Override
    public MyValueResponse<?> walletPayment(Long bookingId) {
        bookingUseCase.walletPayment(DomainKey.of(bookingId));

        return MyValueResponse.successNoData();
    }
}
