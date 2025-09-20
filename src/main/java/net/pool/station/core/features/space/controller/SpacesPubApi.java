package net.pool.station.core.features.space.controller;

import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.features.space.controller.models.SpaceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/v1/pub/spaces")
public interface SpacesPubApi {
    @GetMapping
    MyPageResponse<SpaceResponse> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "")
            String search,

            @RequestParam(required = false, value = "stationId", defaultValue = "")
            Long stationId,

            @RequestParam(required = false, value = "typeCodes", defaultValue = "")
            List<String> typeCodes,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "createdAt_desc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );
}
