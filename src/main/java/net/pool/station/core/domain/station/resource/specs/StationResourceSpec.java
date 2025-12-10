package net.pool.station.core.domain.station.resource.specs;

import lombok.Builder;
import lombok.With;
import net.pool.station.core.bootstrap.enums.EResourceType;
import net.pool.station.core.bootstrap.enums.ESpecType;

@Builder
public record StationResourceSpec(
        Long stationResourceSpecId,

        @With
        Long stationResourceId,

        String pcCpu,

        String pcRam,

        String pcGpu,

        String pcMonitor,

        String pcKeyboard,

        String pcMouse,

        String pcHeadphone,

        String btTableDetail,

        String btCueDetail,

        String btBallDetail,

        String csConsoleModel,

        String csTvModel,

        String csControllerType,

        Integer csControllerCount,

        @With
        String typeCode,

        @With
        String typeName
) {
        public static StationResourceSpec.StationResourceSpecBuilder pcBuilder(Long stationResourceId) {
                return StationResourceSpec.builder()
                        .stationResourceId(stationResourceId)
                        .typeCode(EResourceType.PC.getCode())
                        .typeName(EResourceType.PC.getName());
        }

        public static StationResourceSpec.StationResourceSpecBuilder btBuilder(Long stationResourceId) {
                return StationResourceSpec.builder()
                        .stationResourceId(stationResourceId)
                        .typeCode(EResourceType.BILLIARD_TABLE.getCode())
                        .typeName(EResourceType.BILLIARD_TABLE.getName());
        }

        public static StationResourceSpec.StationResourceSpecBuilder csBuilder(Long stationResourceId) {
                return StationResourceSpec.builder()
                        .typeCode(EResourceType.CONSOLE.getCode())
                        .typeName(EResourceType.CONSOLE.getName());
        }
}
