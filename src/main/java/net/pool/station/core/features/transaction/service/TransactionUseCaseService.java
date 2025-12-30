package net.pool.station.core.features.transaction.service;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.enums.EPaymentStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MySpringContext;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.BookingUseCase;
import net.pool.station.core.domain.match.making.MatchMakingUseCase;
import net.pool.station.core.domain.payment.PaymentWebhook;
import net.pool.station.core.domain.transaction.Transaction;
import net.pool.station.core.domain.transaction.TransactionCriteria;
import net.pool.station.core.domain.transaction.TransactionUseCase;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;
import net.pool.station.core.domain.wallet.ledger.WalletLedgerUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionUseCaseService implements TransactionUseCase {
    TransactionCommandService commandService;

    TransactionQueryService queryService;

    WalletLedgerUseCase walletLedgerUseCase;

    @NonFinal
    @Value("${environment.commission.percent}")
    Integer commission;

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {
        return commandService.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Transaction> findAll(TransactionCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Transaction> findByTransactionCode(DomainKey<String> transactionCode) {
        return queryService.findByTransactionCode(transactionCode.value());
    }

    @Override
    @Transactional
    public void update(DomainKey<String> transactionCode, Transaction transaction) {
        commandService.update(transactionCode.value(), transaction);
    }

    @Override
    @Transactional
    public void update(DomainKey<String> transactionCode, PaymentWebhook paymentWebhook) {
        BookingUseCase bookingUseCase = MySpringContext.getBean(BookingUseCase.class);
        MatchMakingUseCase matchMakingUseCase = MySpringContext.getBean(MatchMakingUseCase.class);
        log.info("Received webhook: {}", paymentWebhook);
        int chargeCommission = 0;
        if (MyObjectUtils.isEquals(paymentWebhook.code(), "00")) {
            log.info("Processing transaction");
            Transaction transaction = Transaction.builder()
                    .statusCode(EPaymentStatus.PAID.getCode())
                    .statusName(EPaymentStatus.PAID.getName())
                    .paymentCompleteAt(LocalDateTime
                            .parse(paymentWebhook.transactionDateTime(),
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();
            Transaction savedTransaction = commandService.update(transactionCode.value(), transaction);
            log.info("Transaction updated: {}", savedTransaction);

            if (MyObjectUtils.isNotEmpty(savedTransaction)) {
                if (MyObjectUtils.isNotEmpty(savedTransaction.bookingId())) {
                    chargeCommission = paymentWebhook.amount() * commission / 100;
                    bookingUseCase.processed(new DomainKey<>(savedTransaction.bookingId())  );
                }
                if (MyObjectUtils.isNotEmpty(savedTransaction.matchMakingId())) {
                    matchMakingUseCase.process(new DomainKey<>(savedTransaction.matchMakingId()));
                }
                WalletLedger walletLedger = WalletLedger.builder()
                        .walletId(savedTransaction.walletId())
                        .transactionId(savedTransaction.transactionId())
                        .changeAmount(paymentWebhook.amount())
                        .chargedCommission(chargeCommission)
                        .build();
                walletLedgerUseCase.save(walletLedger);
                log.info("Transaction completed");
            }

        }


    }
}
