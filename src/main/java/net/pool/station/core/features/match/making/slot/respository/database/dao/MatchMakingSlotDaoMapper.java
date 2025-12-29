package net.pool.station.core.features.match.making.slot.respository.database.dao;

import net.pool.station.core.bootstrap.configuration.mapper.DaoMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlot;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MatchMakingSlotDaoMapper extends DaoMapper<MatchMakingSlotDao, MatchMakingSlot> {
}
