package net.pool.station.core.features.booking.job;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.EBookingStatus;
import net.pool.station.core.bootstrap.enums.EPaymentMethod;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.booking.BookingUseCase;
import net.pool.station.core.domain.payment.PaymentUseCase;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;
import net.pool.station.core.domain.wallet.ledger.WalletLedgerUseCase;
import net.pool.station.core.features.booking.booking.repository.database.BookingEntity;
import net.pool.station.core.features.booking.booking.repository.database.BookingRepository;
import net.pool.station.core.features.booking.booking.service.BookingCommandService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpiredBookingJob implements Job {
    BookingUseCase bookingUseCase;

    WalletLedgerUseCase walletLedgerUseCase;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Long bookingId = jobExecutionContext.getJobDetail()
                .getJobDataMap()
                .getLong("bookingId");
        Booking booking = bookingUseCase
                .finish(new DomainKey<>(bookingId));
        if (MyObjectUtils.isEquals(EPaymentMethod.DIRECT.getCode(), booking.paymentMethodCode())) {
            WalletLedger walletLedger = WalletLedger.builder()
                    .walletId(booking.walletId())
                    .changeAmount(-booking.totalPrice())
                    .build();
            walletLedgerUseCase.save(walletLedger);
        }
    }
}
