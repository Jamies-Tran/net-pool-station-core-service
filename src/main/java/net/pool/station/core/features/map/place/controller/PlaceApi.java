package net.pool.station.core.features.map.place.controller;

import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.features.map.place.controller.models.PlaceResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/v1/pub/autocomplete")
public interface PlaceApi {
    @GetMapping
    MyListResponse<PlaceResponse.PredictionResponse> autocomplete(
            @RequestParam(value = "address")
            String address,
            @RequestParam(required = false, value = "limit", defaultValue = "20")
            Integer limit,
            @RequestParam(required = false, value = "moreCompound", defaultValue = "true")
            Boolean moreCompound
    );
}
