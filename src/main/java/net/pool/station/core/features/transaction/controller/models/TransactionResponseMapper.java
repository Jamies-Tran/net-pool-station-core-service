package net.pool.station.core.features.transaction.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.transaction.Transaction;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface TransactionResponseMapper extends ModelMapper<TransactionResponse, Transaction> {}
