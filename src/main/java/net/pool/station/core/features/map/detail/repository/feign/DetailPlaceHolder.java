package net.pool.station.core.features.map.detail.repository.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "detailPlaceHolder",
        url = "${environment.map.goongDomain}",
        path = "${environment.map.detail}"
)
public interface DetailPlaceHolder {
    @GetMapping
    DetailFeign findDetail(@RequestParam(value = "place_id") String placeId);
}
