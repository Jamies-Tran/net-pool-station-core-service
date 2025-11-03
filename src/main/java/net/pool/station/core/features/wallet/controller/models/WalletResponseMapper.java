package net.pool.station.core.features.wallet.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.wallet.Wallet;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface WalletResponseMapper extends ModelMapper<WalletResponse, Wallet> {
}
