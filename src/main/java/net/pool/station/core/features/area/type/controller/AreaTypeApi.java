package net.pool.station.core.features.area.type.controller;

import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.area.type.controller.models.AreaTypeResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/api/area-types/{areaTypeId}")
public interface AreaTypeApi {
    @GetMapping
    MyValueResponse<AreaTypeResponse> findById(@PathVariable Long areaTypeId);

    @PatchMapping("/enable")
    MyValueResponse<?> enable(@PathVariable Long areaTypeId);

    @PatchMapping("/disable")
    MyValueResponse<?> disable(@PathVariable Long areaTypeId);

    @DeleteMapping
    MyValueResponse<?> delete(@PathVariable Long areaTypeId);
}
