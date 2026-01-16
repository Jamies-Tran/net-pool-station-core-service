package net.pool.station.core.features.transaction.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EPaymentMethod;
import net.pool.station.core.bootstrap.enums.EPaymentStatus;
import net.pool.station.core.bootstrap.enums.EPaymentType;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyPaymentUtils;
import net.pool.station.core.bootstrap.utils.MySpringContext;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.booking.Booking;
import net.pool.station.core.domain.booking.BookingUseCase;
import net.pool.station.core.domain.match.making.MatchMaking;
import net.pool.station.core.domain.match.making.MatchMakingUseCase;
import net.pool.station.core.domain.payment.PaymentWebhook;
import net.pool.station.core.domain.transaction.Transaction;
import net.pool.station.core.domain.transaction.TransactionCriteria;
import net.pool.station.core.domain.transaction.TransactionUseCase;
import net.pool.station.core.domain.wallet.Wallet;
import net.pool.station.core.domain.wallet.WalletUseCase;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;
import net.pool.station.core.domain.wallet.ledger.WalletLedgerUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionUseCaseService implements TransactionUseCase {
    TransactionCommandService commandService;

    TransactionQueryService queryService;

    WalletLedgerUseCase walletLedgerUseCase;

    WalletUseCase walletUseCase;


    private BookingUseCase bookingUseCase() {
        return MySpringContext.getBean(BookingUseCase.class);
    }

    private MatchMakingUseCase matchMakingUseCase() {
        return MySpringContext.getBean(MatchMakingUseCase.class);
    }

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
    public List<Transaction> findAllByIdIn(List<Long> transactionIds) {
        return queryService.findAllByIdIn(transactionIds);
    }

    @Override
    @Transactional
    public void handlePaymentWallet(Booking booking) {
        if (MyObjectUtils.isNotEquals(EPaymentMethod.WALLET.getCode(), booking.paymentMethodCode())) {
            throw new MyResourceNotValid("Booking không thanh toán bằng ví hệ thống.");
        }
        Wallet playerWallet = walletUseCase.findByAccountId(DomainKey.of(Long.valueOf(booking.createdBy())))
                .orElseThrow(MyResourceNotFoundException::new);
        if (playerWallet.balance() < booking.totalPrice()) {
            throw new MyResourceNotValid("Vui lòng nạp thêm %s vào ví để tiếp tục booking."
                    .formatted(booking.totalPrice() - playerWallet.balance()));
        }
        Transaction transaction = Transaction.builder()
                .bookingId(booking.bookingId())
                .walletId(booking.ownerWalletId())
                .amount(booking.totalPrice())
                .paymentMethodCode(booking.paymentMethodCode())
                .paymentMethodName(booking.paymentMethodName())
                .paymentTypeCode(EPaymentType.BOOKING_PAYMENT.getCode())
                .paymentTypeName(EPaymentType.BOOKING_PAYMENT.getName())
                .build();
        Transaction savedTransaction = commandService.save(transaction);
        WalletLedger playerWalletLedger = WalletLedger.builder()
                .walletId(playerWallet.walletId())
                .transactionId(savedTransaction.transactionId())
                .changeAmount(-booking.totalPrice())
                .chargedCommission(0)
                .build();
        WalletLedger ownerWalletLedger = WalletLedger.builder()
                .walletId(booking.ownerWalletId())
                .transactionId(savedTransaction.transactionId())
                .changeAmount(booking.totalPrice())
                .chargedCommission(MyPaymentUtils.calculateCommission(booking.totalPrice()))
                .build();
        walletLedgerUseCase.saveAll(List.of(playerWalletLedger, ownerWalletLedger));
        bookingUseCase().processed(new DomainKey<>(booking.bookingId()));
    }

    @Override
    @Transactional
    public void handlePaymentWallet(MatchMaking matchMaking) {
        if (MyObjectUtils.isNotEquals(EPaymentMethod.WALLET.getCode(), matchMaking.paymentMethodCode())) {
            throw new MyResourceNotValid("Phòng ghép trận không thanh toán bằng ví hệ thống.");
        }
        Wallet playerWallet = walletUseCase.findByAccountId(DomainKey.of(Long.valueOf(matchMaking.createdBy())))
                .orElseThrow(MyResourceNotFoundException::new);
        Integer deposit = MyPaymentUtils.calculateDeposit(matchMaking.totalPrice(), matchMaking.numberOfHoldingDay());
        if (playerWallet.balance() < deposit) {
            throw new MyResourceNotValid("Vui lòng nạp thêm %s vào ví để tiếp tục."
                    .formatted(deposit - playerWallet.balance()));
        }
        Transaction transaction = Transaction.builder()
                .matchMakingId(matchMaking.matchMakingId())
                .walletId(matchMaking.ownerWalletId())
                .amount(deposit)
                .paymentMethodCode(matchMaking.paymentMethodCode())
                .paymentMethodName(matchMaking.paymentMethodName())
                .paymentTypeCode(EPaymentType.MATCH_MAKING_DEPOSIT.getCode())
                .paymentTypeName(EPaymentType.MATCH_MAKING_DEPOSIT.getName())
                .build();
        Transaction savedTransaction = commandService.save(transaction);
        WalletLedger playerWalletLedger = WalletLedger.builder()
                .walletId(playerWallet.walletId())
                .transactionId(savedTransaction.transactionId())
                .changeAmount(-deposit)
                .chargedCommission(0)
                .build();
        WalletLedger ownerWalletLedger = WalletLedger.builder()
                .walletId(matchMaking.ownerWalletId())
                .transactionId(savedTransaction.transactionId())
                .changeAmount(deposit)
                .chargedCommission(0)
                .build();
        walletLedgerUseCase.saveAll(List.of(playerWalletLedger, ownerWalletLedger));
        matchMakingUseCase().process(new DomainKey<>(matchMaking.matchMakingId()), deposit);
    }

    @Override
    @Transactional
    public void handlePaymentWebhook(DomainKey<String> transactionCode, PaymentWebhook paymentWebhook) {
        log.info("Received webhook: {}", paymentWebhook);
        int chargeCommission;
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
                chargeCommission = MyPaymentUtils.calculateCommission(paymentWebhook.amount());
                if (MyObjectUtils.isNotEmpty(savedTransaction.bookingId())) {
                    bookingUseCase().processed(new DomainKey<>(savedTransaction.bookingId())  );
                }
                if (MyObjectUtils.isNotEmpty(savedTransaction.matchMakingId())) {
                    matchMakingUseCase().process(new DomainKey<>(savedTransaction.matchMakingId()), paymentWebhook.amount());
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

    @Override
    @Transactional
    public void handleRefundMatchMaking(MatchMaking matchMaking) {
        if (matchMaking.createdAt().until(LocalDateTime.now(), ChronoUnit.DAYS) <= 1) {
            Optional<Transaction> oldTransactionOpt = queryService
                    .findByMatchMakingIdAndPaymentType(matchMaking.matchMakingId(),
                            EPaymentType.MATCH_MAKING_DEPOSIT);
            if (oldTransactionOpt.isPresent()) {
                Transaction oldTransaction = oldTransactionOpt.get();
                Wallet playerWallet = walletUseCase.findByAccountId(DomainKey.of(Long.valueOf(matchMaking.createdBy())))
                        .orElseThrow(MyResourceNotFoundException::new);
                Transaction transaction = Transaction.builder()
                        .matchMakingId(matchMaking.matchMakingId())
                        .walletId(matchMaking.ownerWalletId())
                        .amount(oldTransaction.amount())
                        .paymentMethodCode(matchMaking.paymentMethodCode())
                        .paymentMethodName(matchMaking.paymentMethodName())
                        .paymentTypeCode(EPaymentType.MATCH_MAKING_DEPOSIT_REFUND.getCode())
                        .paymentTypeName(EPaymentType.MATCH_MAKING_DEPOSIT_REFUND.getName())
                        .build();
                Transaction savedTransaction = commandService.save(transaction);
                WalletLedger playerWalletLedger = WalletLedger.builder()
                        .walletId(playerWallet.walletId())
                        .transactionId(savedTransaction.transactionId())
                        .changeAmount(savedTransaction.amount())
                        .chargedCommission(0)
                        .build();
                WalletLedger ownerWalletLedger = WalletLedger.builder()
                        .walletId(matchMaking.ownerWalletId())
                        .transactionId(savedTransaction.transactionId())
                        .changeAmount(-savedTransaction.amount())
                        .chargedCommission(0)
                        .build();
                walletLedgerUseCase.saveAll(List.of(playerWalletLedger, ownerWalletLedger));
            }

        }
    }


}
