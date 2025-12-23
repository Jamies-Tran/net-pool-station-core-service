package net.pool.station.core.features.station.resource.resource.controller.api.models;

import net.pool.station.core.bootstrap.configuration.mapper.MapStructConfig;
import net.pool.station.core.bootstrap.configuration.mapper.ModelMapper;
import net.pool.station.core.bootstrap.enums.EResourceType;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.station.resource.StationResource;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import net.pool.station.core.features.station.resource.specs.controller.models.StationResourceSpecRequest;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface StationResourceRequestMapper extends ModelMapper<StationResourceRequest, StationResource> {
    default StationResourceSpec map(StationResourceSpecRequest model) {
        if (MyObjectUtils.isEmpty(model)) {
            return null;
        }

        return switch (EResourceType.valueOf(model.typeCode())) {
            case PC -> StationResourceSpec.builder()
                    .pcCpu(model.pc().pcCpu())
                    .pcRam(model.pc().pcRam())
                    .pcGpu(model.pc().pcGpu())
                    .pcMonitor(model.pc().pcMonitor())
                    .pcKeyboard(model.pc().pcKeyboard())
                    .pcMouse(model.pc().pcMouse())
                    .pcHeadphone(model.pc().pcHeadphone())
                    .typeCode(model.typeCode())
                    .typeName(model.typeName())
                    .build();
            case BILLIARD_TABLE -> StationResourceSpec.builder()
                    .btTableDetail(model.billiardTable().btTableDetail())
                    .btCueDetail(model.billiardTable().btCueDetail())
                    .btBallDetail(model.billiardTable().btBallDetail())
                    .typeCode(model.typeCode())
                    .typeName(model.typeName())
                    .build();
            case CONSOLE -> StationResourceSpec.builder()
                    .csConsoleModel(model.console().csConsoleModel())
                    .csTvModel(model.console().csTvModel())
                    .csControllerType(model.console().csControllerType())
                    .csControllerCount(model.console().csControllerCount())
                    .typeCode(model.typeCode())
                    .typeName(model.typeName())
                    .build();
        };
    }
}
