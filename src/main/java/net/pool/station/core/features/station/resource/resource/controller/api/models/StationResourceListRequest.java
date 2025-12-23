package net.pool.station.core.features.station.resource.resource.controller.api.models;

import net.pool.station.core.features.station.resource.specs.controller.models.StationResourceSpecRequest;

import java.util.List;

public record StationResourceListRequest(
        List<StationResourceRequest> stationResources
) {
}
