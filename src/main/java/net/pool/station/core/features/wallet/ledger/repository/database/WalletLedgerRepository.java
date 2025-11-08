package net.pool.station.core.features.wallet.ledger.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletLedgerRepository extends JpaRepository<WalletLedgerEntity, Long> {
    @Query("""
        SELECT w.balance
        FROM WalletLedgerEntity wl
        INNER JOIN WalletEntity w ON wl.walletId = w.walletId
        WHERE w.walletId = :walletId
        """)
    Integer findCurrentBalanceByWalletId(Long walletId);
}
