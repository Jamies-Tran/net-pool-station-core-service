package net.pool.station.core.domain.booking;

import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.payment.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.Optional;

public interface BookingUseCase {
    Booking save(Booking booking);

    Optional<Booking> findById(DomainKey<Long> bookingId);

    Page<Booking> findAll(BookingCriteria criteria, PageRequest pageRequest);

    Optional<Payment> generatePayment(DomainKey<Long> bookingId);

    void walletPayment(DomainKey<Long> bookingId);

    void update(DomainKey<Long> bookingId, Booking booking);

    void processed(DomainKey<Long> bookingId, LocalDateTime paidTotalAt);

    void start(DomainKey<Long> bookingId);

    void autoStart(DomainKey<Long> bookingId);

    Booking finish(DomainKey<Long> bookingId);

    void cancel(DomainKey<Long> bookingId, String cancelReason);

    void delete(DomainKey<Long> bookingId);
}
