package net.pool.station.core.features.wallet.ledger.repository.database;

import net.pool.station.core.domain.wallet.ledger.WalletLedgerCriteria;
import net.pool.station.core.features.wallet.ledger.repository.database.dao.CurrentBalanceAndCommissionDao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletLedgerRepository extends JpaRepository<WalletLedgerEntity, Long> {
    @Query("""
        SELECT 
                w.balance AS currentBalance,
                w.contributedCommission AS currentContributedCommission
        FROM WalletEntity w
        WHERE w.walletId = :walletId
        """)
    CurrentBalanceAndCommissionDao findCurrentBalanceByWalletId(Long walletId);

    @Query("""
        SELECT wl
        FROM WalletLedgerEntity wl
        INNER JOIN WalletEntity w ON w.walletId = wl.walletId
        INNER JOIN AccountEntity a ON a.accountId = w.accountId
        WHERE a.accountId = :#{#criteria.accountId()}
            AND wl.createdAt BETWEEN :#{#criteria.timeRange().get(0)}
                AND :#{#criteria.timeRange().get(1)}
        """)
    Page<WalletLedgerEntity> findAll(WalletLedgerCriteria criteria, PageRequest pageRequest);
}
