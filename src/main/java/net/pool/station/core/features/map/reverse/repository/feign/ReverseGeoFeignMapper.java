package net.pool.station.core.features.map.reverse.repository.feign;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.map.reverse.ReverseGeo;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface ReverseGeoFeignMapper extends ModelMapper<ReverseGeoFeign.ResultFeign, ReverseGeo.Result> {
}
