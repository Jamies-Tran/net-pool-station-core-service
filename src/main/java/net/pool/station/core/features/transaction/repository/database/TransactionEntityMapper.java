package net.pool.station.core.features.transaction.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.transaction.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapStructConfig.class)
public interface TransactionEntityMapper extends EntityMapper<TransactionEntity, Transaction> {
    void update(@MappingTarget TransactionEntity target, Transaction source);
}
