package net.pool.station.core.features.space.controller.models;

import com.fasterxml.jackson.core.type.TypeReference;
import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.bootstrap.configuration.mapper.MyObjectMapper;
import net.pool.station.core.domain.space.Space;
import org.mapstruct.Mapper;

import java.util.Map;

@Mapper(config = MapStructConfig.class)
public interface SpaceRequestMapper extends ModelMapper<SpaceRequest, Space> {
    default Map<String, Object> parseMap(SpaceRequest.MetadataRequest metadata) {
        return MyObjectMapper.convertFromObjectToMap(metadata);
    }

    default SpaceRequest.MetadataRequest parseMetadata(Map<String, Object> objectMap) {
        return MyObjectMapper.convertToObject(objectMap, new TypeReference<>() {});
    }
}
