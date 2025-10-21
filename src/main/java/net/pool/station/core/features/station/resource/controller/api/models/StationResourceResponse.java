package net.pool.station.core.features.station.resource.controller.api.models;

import lombok.Builder;
import net.pool.station.core.domain.station.resource.StationResource;

import java.util.List;

public record StationResourceResponse(
        Long stationResourceId,
        Long areaId,
        String resourceCode,
        String resourceName,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName,
        SpecsResponse specs
) {
    public record SpecsResponse(
            String cpu,
            String ram,
            List<GpuResponse> gpus,
            List<StorageResponse> storages
    ) {
        @Builder
        public record StorageResponse(
                String model,
                String serial,
                String capacity
        ) {}

        @Builder
        public record GpuResponse(
                String name,
                String vRam
        ) {}
    }
}
