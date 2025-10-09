package net.pool.station.core.features.station.resource.controller;

import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/v1/pub/station-resources")
public interface StationResourcesPubApi {
    @GetMapping
    MyPageResponse<?> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "")
            String search,

            @RequestParam
            Long areaId,

            @RequestParam(required = false, value = "typeCodes", defaultValue = "")
            List<String> typeCodes,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "resourceName_asc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );
}
