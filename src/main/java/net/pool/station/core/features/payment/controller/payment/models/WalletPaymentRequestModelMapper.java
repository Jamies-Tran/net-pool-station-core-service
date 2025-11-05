package net.pool.station.core.features.payment.controller.payment.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.transaction.Transaction;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface WalletPaymentRequestModelMapper extends ModelMapper<WalletPaymentRequest, Transaction> {
}
