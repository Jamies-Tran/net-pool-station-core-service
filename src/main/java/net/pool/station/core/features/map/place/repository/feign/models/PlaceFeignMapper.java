package net.pool.station.core.features.map.place.repository.feign.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.map.place.Place;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface PlaceFeignMapper extends ModelMapper<PlaceFeign.PredictionFeign, Place.Prediction> {
}
