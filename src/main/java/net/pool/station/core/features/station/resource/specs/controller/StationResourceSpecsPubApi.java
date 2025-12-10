package net.pool.station.core.features.station.resource.specs.controller;

import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.features.station.resource.specs.controller.models.StationResourceSpecResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/pub/station-resource/specs")
public interface StationResourceSpecsPubApi {

    @GetMapping("/pc/cpu")
    MyListResponse<String> findAllPcCpu(
            @RequestParam(required = false, value = "search", defaultValue = "") String search);

    @GetMapping("/pc/ram")
    MyListResponse<String> findAllPcRam(
            @RequestParam(required = false, value = "search", defaultValue = "") String search);

    @GetMapping("/pc/gpu")
    MyListResponse<String> findAllPcGpu(
            @RequestParam(required = false, value = "search", defaultValue = "") String search);

    @GetMapping("/pc/monitor")
    MyListResponse<String> findAllPcMonitor(
                @RequestParam(required = false, value = "search", defaultValue = "") String search);

    @GetMapping("/pc/keyboard")
    MyListResponse<String> findAllPcKeyboard(
                @RequestParam(required = false, value = "search", defaultValue = "") String search);

    @GetMapping("/pc/mouse")
    MyListResponse<String> findAllPcMouse(
                @RequestParam(required = false, value = "search", defaultValue = "") String search);

    @GetMapping("/pc/headphone")
    MyListResponse<String> findAllPcHeadphone(
                @RequestParam(required = false, value = "search", defaultValue = "") String search);

    @GetMapping("/billiard/table-detail")
    MyListResponse<String> findAllBtTableDetail(
                @RequestParam(required = false, value = "search", defaultValue = "") String search);

    @GetMapping("/billiard/cue-detail")
    MyListResponse<String> findAllBtCueDetail(
                @RequestParam(required = false, value = "search", defaultValue = "") String search);

    @GetMapping("/billiard/ball-detail")
    MyListResponse<String> findAllBtBallDetail(
                @RequestParam(required = false, value = "search", defaultValue = "") String search);

    @GetMapping("/console/model")
    MyListResponse<String> findAllCsConsoleModel(
                @RequestParam(required = false, value = "search", defaultValue = "") String search);

    @GetMapping("/console/tv-model")
    MyListResponse<String> findAllCsTvModel(
                @RequestParam(required = false, value = "search", defaultValue = "") String search);

    @GetMapping("/console/controller-type")
    MyListResponse<String> findAllControllerType(
                @RequestParam(required = false, value = "search", defaultValue = "") String search);

    @GetMapping("/console/controller-count")
    MyListResponse<String> findAllCsControllerCount(
                @RequestParam(required = false, value = "search", defaultValue = "") String search);
}
