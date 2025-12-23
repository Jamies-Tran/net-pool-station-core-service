package net.pool.station.core.features.map.place.repository.feign;

import net.pool.station.core.features.map.place.repository.feign.models.PlaceDetailFeign;
import net.pool.station.core.features.map.place.repository.feign.models.PlaceFeign;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "placePlaceHolder",
        url = "${environment.map.goongDomain}"
)
public interface PlacePlaceHolder {
    @GetMapping("${environment.map.place}")
    PlaceFeign autocomplete(
            @RequestParam(value = "input")
            String address,
            @RequestParam(required = false, value = "limit", defaultValue = "20")
            Integer limit,
            @RequestParam(required = false, value = "more_compound", defaultValue = "true")
            Boolean moreCompound
    );

    @GetMapping("${environment.map.placeDetail}")
    PlaceDetailFeign findDetail(@RequestParam(value = "place_id") String placeId);
}
