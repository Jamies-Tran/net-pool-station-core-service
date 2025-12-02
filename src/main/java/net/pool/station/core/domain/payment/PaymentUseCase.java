package net.pool.station.core.domain.payment;


import net.pool.station.core.domain.booking.Booking;

public interface PaymentUseCase {
    Payment create(Payment payment);

    Payment createFromBooking(Booking booking);

    void walletPayment(Booking booking);
}
