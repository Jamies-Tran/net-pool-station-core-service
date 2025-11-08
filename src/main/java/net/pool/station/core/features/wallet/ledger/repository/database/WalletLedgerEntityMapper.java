package net.pool.station.core.features.wallet.ledger.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface WalletLedgerEntityMapper extends EntityMapper<WalletLedgerEntity, WalletLedger> {
}
