package net.pool.station.core.features.wallet.ledger.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.wallet.ledger.WalletLedger;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface WalletLedgerResponseMapper extends ModelMapper<WalletLedgerResponse, WalletLedger> {
}
