package net.pool.station.core.features.wallet.wallet.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.wallet.Wallet;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface WalletEntityMapper extends EntityMapper<WalletEntity, Wallet> {
}
