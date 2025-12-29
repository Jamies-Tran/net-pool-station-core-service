package net.pool.station.core.features.match.making.slot.respository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.match.making.slot.MatchMakingSlot;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MatchMakingSlotMapper extends EntityMapper<MatchMakingSlotEntity, MatchMakingSlot> {
}
