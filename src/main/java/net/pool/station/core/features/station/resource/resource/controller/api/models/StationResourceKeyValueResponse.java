package net.pool.station.core.features.station.resource.resource.controller.api.models;

import lombok.Builder;
import net.pool.station.core.features.station.resource.specs.controller.models.StationResourceSpecResponse;

import java.util.List;

@Builder
public record StationResourceKeyValueResponse(
        RowResponse key,
        List<StationResourceResponse> value
) {

}
