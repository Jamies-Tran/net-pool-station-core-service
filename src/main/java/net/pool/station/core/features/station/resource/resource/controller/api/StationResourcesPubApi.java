package net.pool.station.core.features.station.resource.resource.controller.api;

import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.bootstrap.rest.response.MyMapResponse;
import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.domain.station.resource.Row;
import net.pool.station.core.features.station.resource.resource.controller.api.models.RowResponse;
import net.pool.station.core.features.station.resource.resource.controller.api.models.StationResourceKeyValueResponse;
import net.pool.station.core.features.station.resource.resource.controller.api.models.StationResourceResponse;
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

            @RequestParam(required = false, value = "stationSpaceId", defaultValue = "")
            Long stationSpaceId,

            @RequestParam(required = false, value = "areaId", defaultValue = "")
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


    @GetMapping("/row")
    MyListResponse<StationResourceKeyValueResponse> findAllMapByRow(
            @RequestParam(required = false, value = "search", defaultValue = "")
            String search,

            @RequestParam(required = false, value = "stationSpaceId", defaultValue = "")
            Long stationSpaceId,

            @RequestParam(required = false, value = "areaId", defaultValue = "")
            Long areaId,

            @RequestParam(required = false, value = "typeCodes", defaultValue = "")
            List<String> typeCodes,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "displayOrder_asc")
            String sorter,

            @RequestParam(required = false, value = "current", defaultValue = "0")
            Integer current,

            @RequestParam(required = false, value = "pageSize", defaultValue = "25")
            Integer pageSize
    );
}
