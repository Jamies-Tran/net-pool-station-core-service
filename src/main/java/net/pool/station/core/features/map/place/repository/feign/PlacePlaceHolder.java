package net.pool.station.core.features.map.place.repository.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "placePlaceHolder",
        url = "${environment.map.goongDomain}",
        path = "${environment.map.place}"
)
public interface PlacePlaceHolder {
    @GetMapping
    PlaceFeign autocomplete(
            @RequestParam(value = "input")
            String address,
            @RequestParam(required = false, value = "limit", defaultValue = "20")
            Integer limit,
            @RequestParam(required = false, value = "more_compound", defaultValue = "true")
            Boolean moreCompound
    );
}
