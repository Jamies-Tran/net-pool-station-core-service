package net.pool.station.core.features.station.resource.specs.controller.models;

import lombok.Builder;
import net.pool.station.core.bootstrap.enums.ESpecType;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import net.pool.station.core.features.station.resource.specs.controller.models.bt.BilliardTableSpecResponse;
import net.pool.station.core.features.station.resource.specs.controller.models.cs.ConsoleSpecResponse;
import net.pool.station.core.features.station.resource.specs.controller.models.pc.PcSpecResponse;

@Builder
public record StationResourceSpecResponse(
        Long stationResourceId,
        PcSpecResponse pc,
        BilliardTableSpecResponse billiardTable,
        ConsoleSpecResponse console,
        String typeCode,
        String typeName
) {
    public static StationResourceSpecResponse of(StationResourceSpec spec) {
        if (MyObjectUtils.isEmpty(spec)) {
            return null;
        }
        StationResourceSpecResponse.StationResourceSpecResponseBuilder builder = StationResourceSpecResponse
                .builder()
                .stationResourceId(spec.stationResourceId())
                .typeCode(spec.typeCode())
                .typeName(spec.typeName());

        return switch (ESpecType.valueOf(spec.typeCode())) {
            case PC -> builder
                    .pc(PcSpecResponse.builder()
                            .pcCpu(spec.pcCpu())
                            .pcRam(spec.pcRam())
                            .pcGpu(spec.pcGpu())
                            .pcMonitor(spec.pcMonitor())
                            .pcKeyboard(spec.pcKeyboard())
                            .pcMouse(spec.pcMouse())
                            .pcHeadphone(spec.pcHeadphone())
                            .build())
                    .build();
            case Billiard_TABLE -> builder
                    .billiardTable(BilliardTableSpecResponse.builder()
                            .btTableDetail(spec.btTableDetail())
                            .btCueDetail(spec.btCueDetail())
                            .btBallDetail(spec.btBallDetail())
                            .build())
                    .build();
            case CONSOLE -> builder
                    .console(ConsoleSpecResponse.builder()
                            .csConsoleModel(spec.csConsoleModel())
                            .csTvModel(spec.csTvModel())
                            .csControllerType(spec.csControllerType())
                            .csControllerCount(spec.csControllerCount())
                            .build())
                    .build();
        };
    }
}
