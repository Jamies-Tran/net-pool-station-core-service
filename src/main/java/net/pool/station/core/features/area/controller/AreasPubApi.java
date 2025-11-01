package net.pool.station.core.features.area.controller;

import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.features.area.controller.models.AreaResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/v1/pub/areas")
public interface AreasPubApi {
    @GetMapping
    MyPageResponse<AreaResponse> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "")
            String search,

            @RequestParam
            Long stationId,

            @RequestParam(required = false, value = "spaceId", defaultValue = "0")
            Long spaceId,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "typeCodes", defaultValue = "")
            List<String> typeCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "areaName_asc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );
}
