package net.pool.station.core.domain.station.resource;

import lombok.Builder;
import lombok.With;

import java.util.List;

public record StationResource(
        Long stationResourceId,
        @With Long areaId,
        String resourceCode,
        String resourceName,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName,
        Specs specs
) {
    public record AreaId(
            Long areaId
    ) {}

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
        ) {}

        @Builder
        public record Gpu(
                String name,
                String vRam
        ) {}
    }
}
