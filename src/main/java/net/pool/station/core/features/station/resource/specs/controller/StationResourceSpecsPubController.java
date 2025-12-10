package net.pool.station.core.features.station.resource.specs.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpecCriteria;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpecUseCase;
import net.pool.station.core.features.station.resource.specs.controller.models.StationResourceSpecResponse;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceSpecsPubController implements StationResourceSpecsPubApi {
    StationResourceSpecUseCase stationResourceSpecUseCase;

    @Override
    public MyListResponse<StationResourceSpecResponse> findAll(
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
            Integer csControllerCount
    ) {
        StationResourceSpecCriteria criteria = StationResourceSpecCriteria.builder()
                .pcCpu(pcCpu)
                .pcRam(pcRam)
                .pcGpu(pcGpu)
                .pcMonitor(pcMonitor)
                .pcKeyboard(pcKeyboard)
                .pcMouse(pcMouse)
                .pcHeadphone(pcHeadphone)
                .btTableDetail(btTableDetail)
                .btCueDetail(btCueDetail)
                .btBallDetail(btBallDetail)
                .csConsoleModel(csConsoleModel)
                .csTvModel(csTvModel)
                .csControllerType(csControllerType)
                .csControllerCount(csControllerCount)
                .build();
        List<StationResourceSpec> specs = stationResourceSpecUseCase.findAll(criteria);

        return MyListResponse.success(StationResourceSpecResponse.of(specs));
    }
}
