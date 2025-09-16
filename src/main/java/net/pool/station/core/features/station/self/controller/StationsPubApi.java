package net.pool.station.core.features.station.self.controller;

import net.pool.station.core.bootstrap.rest.response.MyPageResponse;
import net.pool.station.core.features.station.self.controller.models.StationResponse;
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

            @RequestParam(required = false, value = "distance", defaultValue = "")
            Double distance,

            @RequestParam(required = false, value = "timeRange", defaultValue = "")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            List<LocalDateTime> timeRange,

            @RequestParam(required = false, value = "statusCodes", defaultValue = "")
            List<String> statusCodes,

            @RequestParam(required = false, value = "sorter", defaultValue = "createdAt_desc")
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
