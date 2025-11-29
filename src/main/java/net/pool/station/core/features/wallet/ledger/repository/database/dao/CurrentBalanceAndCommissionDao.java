package net.pool.station.core.features.wallet.ledger.repository.database.dao;

public interface CurrentBalanceAndCommissionDao {
    Integer getCurrentBalance();

    Integer getCurrentContributedCommission();
}
