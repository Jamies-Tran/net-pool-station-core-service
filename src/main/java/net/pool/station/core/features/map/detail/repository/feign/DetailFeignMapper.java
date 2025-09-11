package net.pool.station.core.features.map.detail.repository.feign;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.map.detail.Detail;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface DetailFeignMapper extends ModelMapper<DetailFeign.ResultFeign,
        Detail.Result> {
}
