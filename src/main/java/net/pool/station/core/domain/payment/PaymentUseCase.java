package net.pool.station.core.domain.payment;


import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.match.making.MatchMaking;
import net.pool.station.core.domain.match.participant.MatchParticipant;

public interface PaymentUseCase {
    Payment create(Payment payment);

    Payment createFromBooking(Booking booking);

    Payment createDepositFromMatchMaking(MatchMaking matchMaking);

    void walletPaymentForBooking(Booking booking);

    void depositWalletPaymentForMatchMaking(MatchMaking matchMaking);

    Payment createFromMatchParticipant(MatchParticipant matchParticipant);

    void walletPaymentForMatchParticipant(MatchParticipant matchParticipant);

    void refundMatchMakingDeposit(MatchMaking matchMaking);

    void refundMatchParticipantShare(MatchParticipant matchParticipant);

    void paymentToStartMatchMaking(MatchMaking matchMaking);
}
