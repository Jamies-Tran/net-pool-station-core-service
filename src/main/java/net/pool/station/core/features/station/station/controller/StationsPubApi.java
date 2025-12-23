package net.pool.station.core.features.station.station.controller;

import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.features.station.station.controller.models.StationResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/v1/pub/stations")
public interface StationsPubApi {
    @GetMapping
    MyPageResponse<StationResponse> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "")
            String search,

            @RequestParam(required = false, value = "province", defaultValue = "")
            String province,

            @RequestParam(required = false, value = "commune", defaultValue = "")
            String commune,

            @RequestParam(required = false, value = "district", defaultValue = "")
            String district,

            @RequestParam(required = false, value = "latitude", defaultValue = "0.0")
            Double latitude,

            @RequestParam(required = false, value = "longitude", defaultValue = "0.0")
            Double longitude,

            @RequestParam(required = false, value = "gameName", defaultValue = "")
            String gameName,

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
            Integer csControllerCount,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "distance_asc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );

    @GetMapping("/province")
    MyPageResponse<String> findAllStationProvince(
            @RequestParam(required = false, value = "province", defaultValue = "")
            String province,

            @RequestParam(required = false, value = "sorter", defaultValue = "province_asc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );

    @GetMapping("/commune")
    MyPageResponse<String> findAllStationCommune(
            @RequestParam(required = false, value = "commune", defaultValue = "")
            String commune,

            @RequestParam(required = false, value = "sorter", defaultValue = "commune_asc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );

    @GetMapping("/district")
    MyPageResponse<String> findAllStationDistrict(
            @RequestParam(required = false, value = "district", defaultValue = "")
            String district,

            @RequestParam(required = false, value = "sorter", defaultValue = "district_asc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );
}
