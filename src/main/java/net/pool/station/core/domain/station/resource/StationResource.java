package net.pool.station.core.domain.station.resource;

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
        PcSpec pcSpec,
        BilliardTableSpec billiardTableSpec,
        ConsoleSpec consoleSpec
    ) {
        public record PcSpec(
                String cpu,
                String ram,
                String gpu,
                List<Storage> storages,
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
