package net.pool.station.core.domain.payment;


import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.match.making.MatchMaking;

public interface PaymentUseCase {
    Payment create(Payment payment);

    Payment createFromBooking(Booking booking);

    Payment createFromMatchMaking(MatchMaking matchMaking);

    void walletPayment(Booking booking);
}
