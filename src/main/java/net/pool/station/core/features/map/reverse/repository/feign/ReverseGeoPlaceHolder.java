package net.pool.station.core.features.map.reverse.repository.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "reverseGeoPlaceHolder",
        url = "${environment.map.goongDomain}",
        path = "${environment.map.reverseGeo}"
)
public interface ReverseGeoPlaceHolder {
    @GetMapping
    ReverseGeoFeign findReverseGeo(@RequestParam(value = "latlng") String search);
}
