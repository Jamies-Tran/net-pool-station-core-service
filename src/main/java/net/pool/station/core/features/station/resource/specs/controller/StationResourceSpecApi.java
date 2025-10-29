package net.pool.station.core.features.station.resource.specs.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.station.resource.specs.controller.models.bt.BilliardTableSpecRequest;
import net.pool.station.core.features.station.resource.specs.controller.models.cs.ConsoleSpecRequest;
import net.pool.station.core.features.station.resource.specs.controller.models.pc.PCSpecsRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/station-resource/specs/{areaId}")
public interface StationResourceSpecApi {
    @PostMapping("/pc")
    MyValueResponse<?> saveForPc(@PathVariable Long areaId, @RequestBody PCSpecsRequest request);

    @PostMapping("/billiard-table")
    MyValueResponse<?> saveForBilliardTable(@PathVariable Long areaId, @RequestBody BilliardTableSpecRequest request);

    @PostMapping("/console")
    MyValueResponse<?> saveForConsole(@PathVariable Long areaId, @RequestBody ConsoleSpecRequest request);


}
