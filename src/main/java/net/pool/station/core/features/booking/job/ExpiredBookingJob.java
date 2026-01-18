package net.pool.station.core.features.booking.job;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.pool.station.core.bootstrap.enums.EBookingStatus;
import net.pool.station.core.bootstrap.enums.EPaymentMethod;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.booking.BookingUseCase;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;
import net.pool.station.core.domain.wallet.ledger.WalletLedgerUseCase;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpiredBookingJob implements Job {
    BookingUseCase bookingUseCase;

    WalletLedgerUseCase walletLedgerUseCase;

    @NonFinal
    @Value("${environment.commission.percent}")
    Integer commission;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Long bookingId = jobExecutionContext.getJobDetail()
                .getJobDataMap()
                .getLong("bookingId");
        Booking booking = bookingUseCase
                .finish(new DomainKey<>(bookingId));
        if (MyObjectUtils.isEquals(EPaymentMethod.DIRECT, EPaymentMethod.valueOf(booking.paymentMethodCode()))
            && MyObjectUtils.isEquals(EBookingStatus.COMPLETED.getCode(), booking.statusCode())) {
            int chargeCommission = booking.totalPrice() * commission/100;
            WalletLedger walletLedger = WalletLedger.builder()
                    .walletId(booking.ownerWalletId())
                    .changeAmount(-booking.totalPrice())
                    .chargedCommission(chargeCommission)
                    .build();
            walletLedgerUseCase.save(walletLedger, true);
        }
    }
}
