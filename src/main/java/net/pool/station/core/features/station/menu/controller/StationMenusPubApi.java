package net.pool.station.core.features.station.menu.controller;

import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.features.station.menu.controller.models.StationMenuResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("/v1/pub/station-menus")
public interface StationMenusPubApi {
    @GetMapping
    MyPageResponse<StationMenuResponse> findAll(
            @RequestParam(required = false, value = "search", defaultValue = "")
            String search,

            @RequestParam
            Long stationId,

            @RequestParam(required = false, value = "priceRange", defaultValue = "")
            List<Long> priceRange,

            @RequestParam(required = false, value = "typeCodes", defaultValue = "")
            List<String> typeCodes,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "menuName_asc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );
}
