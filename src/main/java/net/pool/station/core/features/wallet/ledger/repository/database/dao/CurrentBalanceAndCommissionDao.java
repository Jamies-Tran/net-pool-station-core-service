package net.pool.station.core.features.wallet.ledger.repository.database.dao;

public interface CurrentBalanceAndCommissionDao {
    Long getWalletId();

    Integer getCurrentBalance();

    Integer getCurrentContributedCommission();
}
