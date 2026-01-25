package net.pool.station.core.domain.payment;


import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.match.making.MatchMaking;
import net.pool.station.core.domain.match.participant.MatchParticipant;

public interface PaymentUseCase {
    Payment create(Payment payment);

    Payment createFromBooking(Booking booking);

    void walletPaymentForBooking(Booking booking);

    void directPaymentForBooking(Booking booking);

    Payment createDepositFromMatchMaking(MatchMaking matchMaking);

    void depositWalletPaymentForMatchMaking(MatchMaking matchMaking);

    Payment createFromMatchParticipant(MatchParticipant matchParticipant);

    void walletPaymentForMatchParticipant(MatchParticipant matchParticipant);

    void directPaymentForMatchParticipant(MatchParticipant matchParticipant);

    void refundMatchMakingDeposit(MatchMaking matchMaking);

    void refundMatchParticipantShare(MatchParticipant matchParticipant);

    void paymentToStartMatchMaking(MatchMaking matchMaking);

    void payDeposit(MatchMaking matchMaking);
}
