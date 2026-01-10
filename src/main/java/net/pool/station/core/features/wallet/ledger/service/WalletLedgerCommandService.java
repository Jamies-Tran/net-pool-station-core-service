package net.pool.station.core.features.wallet.ledger.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;
import net.pool.station.core.features.wallet.ledger.repository.database.WalletLedgerEntity;
import net.pool.station.core.features.wallet.ledger.repository.database.WalletLedgerEntityMapper;
import net.pool.station.core.features.wallet.ledger.repository.database.WalletLedgerRepository;
import net.pool.station.core.features.wallet.ledger.repository.database.dao.CurrentBalanceAndCommissionDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletLedgerCommandService {
    WalletLedgerRepository repository;

    WalletLedgerEntityMapper mapper;

    protected WalletLedger save(WalletLedger walletLedger) {
        CurrentBalanceAndCommissionDao currentBalanceAndCommission = repository
                .findCurrentBalanceByWalletId(walletLedger.walletId());
        Integer changeAmount = walletLedger.changeAmount();
        Integer chargeCommission = walletLedger.chargedCommission();
        Integer currentBalance = currentBalanceAndCommission.getCurrentBalance();
        Integer currentContributedCommission = currentBalanceAndCommission.getCurrentContributedCommission();
        Integer newBalance = currentBalance + changeAmount - chargeCommission;
        Integer newContributedCommission = currentContributedCommission + chargeCommission;
        WalletLedgerEntity saveLedger = repository.save(mapper
                .toEntity(walletLedger
                        .withCurrentBalance(currentBalance)
                        .withNewBalance(newBalance)
                        .withChargedCommission(chargeCommission)
                        .withNewContributedCommission(newContributedCommission)));

        return mapper.toDto(saveLedger);
    }

    protected List<WalletLedger> saveAll(List<WalletLedger> walletLedgers) {
        List<Long> walletIds = walletLedgers.stream().map(WalletLedger::walletId).toList();
        Map<Long, CurrentBalanceAndCommissionDao> currentBalanceAndCommissionMap = repository
                .findCurrentBalanceByWalletIdIn(walletIds)
                .stream()
                .collect(Collectors.toMap(CurrentBalanceAndCommissionDao::getWalletId, Function.identity()));
        walletLedgers = walletLedgers.stream()
                .map(w -> {
                    Optional<CurrentBalanceAndCommissionDao> currentBalanceAndCommission = Optional
                            .ofNullable(currentBalanceAndCommissionMap
                                    .computeIfAbsent(w.walletId(), k -> null));
                    Integer changeAmount = w.changeAmount();
                    Integer chargeCommission = w.chargedCommission();
                    Integer currentBalance = currentBalanceAndCommission
                            .map(CurrentBalanceAndCommissionDao::getCurrentBalance)
                            .orElseThrow(MyResourceDuplicateException::new);
                    Integer currentContributedCommission = currentBalanceAndCommission
                            .map(CurrentBalanceAndCommissionDao::getCurrentContributedCommission)
                            .orElseThrow(MyResourceDuplicateException::new);
                    Integer newBalance = currentBalance + changeAmount - chargeCommission;
                    Integer newContributedCommission = currentContributedCommission + chargeCommission;

                    return w
                            .withCurrentBalance(currentBalance)
                            .withNewBalance(newBalance)
                            .withChargedCommission(chargeCommission)
                            .withNewContributedCommission(newContributedCommission);
                }).toList();
        List<WalletLedgerEntity> savedWalletLedgers = repository.saveAll(mapper.toEntity(walletLedgers));

        return mapper.toDto(savedWalletLedgers);
    }
}
