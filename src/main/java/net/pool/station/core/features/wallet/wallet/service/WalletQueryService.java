package net.pool.station.core.features.wallet.wallet.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.wallet.Wallet;
import net.pool.station.core.features.wallet.wallet.repository.database.WalletEntityMapper;
import net.pool.station.core.features.wallet.wallet.repository.database.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class WalletQueryService {
    WalletRepository repository;

    WalletEntityMapper mapper;

    protected Optional<Wallet> findByAccountId(Long accountId) {
        return repository.findByAccountId(accountId)
                .map(mapper::toDto);
    }
}
