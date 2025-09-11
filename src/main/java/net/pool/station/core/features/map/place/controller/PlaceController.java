package net.pool.station.core.features.map.place.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyListResponse;
import net.pool.station.core.domain.map.place.PlaceUseCase;
import net.pool.station.core.features.map.place.controller.models.PlaceResponse;
import net.pool.station.core.features.map.place.controller.models.PlaceResponseMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlaceController implements PlaceApi {
    PlaceUseCase placeUseCase;

    PlaceResponseMapper responseMapper;

    @Override
    public MyListResponse<PlaceResponse.PredictionResponse> autocomplete(
            String address,
            Integer limit,
            Boolean moreCompound
    ) {
        List<PlaceResponse.PredictionResponse> responses = responseMapper.toModel(placeUseCase
                .autocomplete(
                        address,
                        limit,
                        moreCompound
                ));

        return MyListResponse.success(responses);
    }
}
