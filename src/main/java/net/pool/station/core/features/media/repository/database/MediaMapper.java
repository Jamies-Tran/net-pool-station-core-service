package net.pool.station.core.features.media.repository.database;

import net.pool.station.core.bootstrap.configuration.mapper.EntityMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.domain.media.Media;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface MediaMapper extends EntityMapper<MediaEntity, Media> {
}
