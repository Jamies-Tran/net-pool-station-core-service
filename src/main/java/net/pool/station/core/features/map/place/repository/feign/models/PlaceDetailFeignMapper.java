package net.pool.station.core.features.map.place.repository.feign.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.map.place.detail.PlaceDetail;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface PlaceDetailFeignMapper extends ModelMapper<PlaceDetailFeign.ResultFeign, PlaceDetail.Result> {

}
