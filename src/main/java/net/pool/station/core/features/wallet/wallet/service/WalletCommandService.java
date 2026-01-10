package net.pool.station.core.features.wallet.wallet.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EWalletStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.wallet.Wallet;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;
import net.pool.station.core.features.wallet.wallet.repository.database.WalletEntity;
import net.pool.station.core.features.wallet.wallet.repository.database.WalletEntityMapper;
import net.pool.station.core.features.wallet.wallet.repository.database.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletCommandService {
    WalletRepository repository;

    WalletEntityMapper mapper;

    protected void save(Long accountId, Wallet wallet) {
        repository.findByAccountId(accountId)
                .ifPresentOrElse(
                        foundEntity -> {},
                        () -> {
                            repository.save(mapper.toEntity(wallet
                                    .withAccountId(accountId)));
                        }
                );
    }

    protected void updateStatus(Long accountId, EWalletStatus status) {
        repository.findByAccountId(accountId)
                .ifPresentOrElse(
                        foundEntity -> {
                            foundEntity.setStatusCode(status.getCode());
                            foundEntity.setStatusName(status.getName());
                            repository.save(foundEntity);
                        },
                        () -> {
                        }
                );
    }

    protected void updateBalance(Long walletId, Integer balance, Integer contributedCommission) {
        repository.findById(walletId)
                .ifPresentOrElse(
                        foundEntity -> {
                            if (MyObjectUtils.isNotEquals(foundEntity.getStatusCode(), EWalletStatus.ENABLE.getCode())) {
                                throw new MyResourceNotValid("Ví hệ thống của bạn chưa được kích hoạt");
                            }
                            foundEntity.setBalance(balance);
                            foundEntity.setContributedCommission(contributedCommission);
                            repository.save(foundEntity);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void updateBalance(List<WalletLedger> walletLedgers) {
        List<Long> walletIds = walletLedgers.stream().map(WalletLedger::walletId).toList();
        Map<Long, WalletLedger> walletLedgerMap = walletLedgers
                .stream()
                .collect(Collectors.toMap(WalletLedger::walletId, Function.identity()));
        List<WalletEntity> newWalletEntity = repository.findAllByWalletIdIn(walletIds)
                .stream()
                .peek(
                        w -> {
                            Optional<WalletLedger> walletLedger = Optional.ofNullable(walletLedgerMap
                                    .computeIfAbsent(w.getWalletId(), k -> null));
                            if (MyObjectUtils.isNotEquals(w.getStatusCode(), EWalletStatus.ENABLE.getCode())) {
                                throw new MyResourceNotValid("Ví hệ thống của bạn chưa được kích hoạt");
                            }
                            w.setBalance(walletLedger.map(WalletLedger::newBalance)
                                    .orElseThrow(MyResourceNotFoundException::new));
                            w.setContributedCommission(walletLedger.map(WalletLedger::newContributedCommission)
                                    .orElseThrow(MyResourceNotFoundException::new));
                        }
                )
                .toList();

        repository.saveAll(newWalletEntity);
    }
}
