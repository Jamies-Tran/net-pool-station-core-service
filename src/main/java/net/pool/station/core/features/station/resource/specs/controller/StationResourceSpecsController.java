package net.pool.station.core.features.station.resource.specs.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.enums.ESpecType;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpecUseCase;
import net.pool.station.core.features.station.resource.specs.controller.models.bt.BilliardTableSpecRequest;
import net.pool.station.core.features.station.resource.specs.controller.models.cs.ConsoleSpecRequest;
import net.pool.station.core.features.station.resource.specs.controller.models.pc.PcSpecRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceSpecsController implements StationResourceSpecApi {
    StationResourceSpecUseCase stationResourceSpecUseCase;

    @Override
    public MyValueResponse<?> saveForPc(Long stationResourceId, PcSpecRequest request) {
        StationResourceSpec spec = StationResourceSpec.pcBuilder(stationResourceId)
                .pcCpu(request.pcCpu())
                .pcRam(request.pcRam())
                .pcGpu(request.pcGpu())
                .pcMonitor(request.pcMonitor())
                .pcKeyboard(request.pcKeyboard())
                .pcMouse(request.pcMouse())
                .pcHeadphone(request.pcHeadphone())
                .build();
        stationResourceSpecUseCase.save(spec);

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> saveForBilliardTable(Long stationResourceId, BilliardTableSpecRequest request) {
        StationResourceSpec spec = StationResourceSpec.btBuilder(stationResourceId)
                .btTableDetail(request.btTableDetail())
                .btCueDetail(request.btCueDetail())
                .btBallDetail(request.btBallDetail())
                .build();
        stationResourceSpecUseCase.save(spec);

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> saveForConsole(Long stationResourceId, ConsoleSpecRequest request) {
        StationResourceSpec spec = StationResourceSpec.csBuilder(stationResourceId)
                .csConsoleModel(request.csConsoleModel())
                .csTvModel(request.csTvModel())
                .csControllerType(request.csControllerType())
                .csControllerCount(request.csControllerCount())
                .build();
        stationResourceSpecUseCase.save(spec);

        return MyValueResponse.successNoData();
    }
}
