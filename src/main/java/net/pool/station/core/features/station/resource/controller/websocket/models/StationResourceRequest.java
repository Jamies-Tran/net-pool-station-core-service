package net.pool.station.core.features.station.resource.controller.websocket.models;

import lombok.Builder;

import java.util.List;

public record StationResourceRequest(
        String resourceCode,
        String resourceName,
        Specs specs
) {
    @Builder
    public record Specs(
            String cpu,
            String ram,
            List<Gpu> gpus,
            List<Storage> storages
    ) {
        @Builder
        public record Storage(
                String model,
                String serial,
                String capacity
        ) {
        }

        @Builder
        public record Gpu(
                String name,
                String vRam
        ) {
        }
    }
}
