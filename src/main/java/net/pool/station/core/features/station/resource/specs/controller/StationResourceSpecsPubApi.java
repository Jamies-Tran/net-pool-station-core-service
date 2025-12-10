package net.pool.station.core.features.station.resource.specs.controller;

import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.features.station.resource.specs.controller.models.StationResourceSpecResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/pub/station-resource/specs")
public interface StationResourceSpecsPubApi {
    @GetMapping
    MyListResponse<StationResourceSpecResponse> findAll(
            @RequestParam(required = false, value = "pcCpu", defaultValue = "")
            String pcCpu,

            @RequestParam(required = false, value = "pcRam", defaultValue = "")
            String pcRam,

            @RequestParam(required = false, value = "pcGpu", defaultValue = "")
            String pcGpu,

            @RequestParam(required = false, value = "pcMonitor", defaultValue = "")
            String pcMonitor,

            @RequestParam(required = false, value = "pcKeyboard", defaultValue = "")
            String pcKeyboard,

            @RequestParam(required = false, value = "pcMouse", defaultValue = "")
            String pcMouse,

            @RequestParam(required = false, value = "pcHeadphone", defaultValue = "")
            String pcHeadphone,

            @RequestParam(required = false, value = "btTableDetail", defaultValue = "")
            String btTableDetail,

            @RequestParam(required = false, value = "btCueDetail", defaultValue = "")
            String btCueDetail,

            @RequestParam(required = false, value = "btBallDetail", defaultValue = "")
            String btBallDetail,

            @RequestParam(required = false, value = "csConsoleModel", defaultValue = "")
            String csConsoleModel,

            @RequestParam(required = false, value = "csTvModel", defaultValue = "")
            String csTvModel,

            @RequestParam(required = false, value = "csControllerType", defaultValue = "")
            String csControllerType,

            @RequestParam(required = false, value = "csControllerCount", defaultValue = "")
            Integer csControllerCount
    );
}
