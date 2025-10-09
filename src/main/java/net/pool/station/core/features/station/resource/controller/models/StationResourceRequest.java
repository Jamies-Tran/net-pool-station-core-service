package net.pool.station.core.features.station.resource.controller.models;

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
            StationResource.Specs.PcSpec pcSpec,
            StationResource.Specs.BilliardTableSpec billiardTableSpec,
            StationResource.Specs.ConsoleSpec consoleSpec
    ) {
        public record PcSpec(
                String cpu,
                String ram,
                String gpu,
                List<StationResource.Specs.PcSpec.Storage> storages,
                String mainboard
        ) {
            public record Storage(
                    String type,
                    String capacity
            ) {}
        }

        public record BilliardTableSpec(
                String typeCode,
                String typeName,
                String surfaceTypeCode,
                String surfaceTypeName,
                String clothTypeCode,
                String clothTypeName
        ) {}

        public record ConsoleSpec (
                Double tvScreenSize,
                String resolution,
                String refreshRate,
                String inputLag
        ) {}
    }
}
