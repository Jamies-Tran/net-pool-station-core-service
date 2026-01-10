package net.pool.station.core.features.wallet.wallet.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
    Optional<WalletEntity> findByAccountId(Long accountId);

    List<WalletEntity> findAllByWalletIdIn(List<Long> walletIds);
}
