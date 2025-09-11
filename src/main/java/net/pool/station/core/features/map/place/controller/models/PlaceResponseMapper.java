package net.pool.station.core.features.map.place.controller.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.domain.map.place.Place;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface PlaceResponseMapper extends ModelMapper<PlaceResponse.PredictionResponse,
        Place.Prediction> {
}
