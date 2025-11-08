package net.pool.station.core.features.wallet.wallet.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotFoundException;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.enums.EWalletStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.wallet.Wallet;
import net.pool.station.core.features.wallet.wallet.repository.database.WalletEntityMapper;
import net.pool.station.core.features.wallet.wallet.repository.database.WalletRepository;
import org.springframework.stereotype.Service;

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
                           throw new MyResourceNotFoundException();
                        }
                );
    }

    protected void updateBalance(Long walletId, Integer balance) {
        repository.findById(walletId)
                .ifPresentOrElse(
                        foundEntity -> {
                            if (MyObjectUtils.isNotEquals(foundEntity.getStatusCode(), EWalletStatus.ENABLE.getCode())) {
                                throw new MyResourceNotValid();
                            }
                            foundEntity.setBalance(balance);
                            repository.save(foundEntity);
                        },
                        () -> {
                            throw new MyResourceNotFoundException();
                        }
                );
    }
}
