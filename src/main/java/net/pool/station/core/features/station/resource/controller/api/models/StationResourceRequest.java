package net.pool.station.core.features.station.resource.controller.api.models;

import lombok.Builder;
import net.pool.station.core.domain.station.resource.StationResource;

import java.util.List;

public record StationResourceRequest(
        Long areaId,
        String resourceCode,
        String resourceName,
        String typeCode,
        String typeName,
        SpecsRequest specs
) {
    public record SpecsRequest(
            String cpu,
            String ram,
            List<GpuRequest> gpus,
            List<StorageRequest> storages
    ) {
        @Builder
        public record StorageRequest(
                String model,
                String serial,
                String capacity
        ) {}

        @Builder
        public record GpuRequest(
                String name,
                String vRam
        ) {}
    }
}
