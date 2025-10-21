package net.pool.station.core.features.station.resource.controller.internal.models;

import lombok.Builder;
import net.pool.station.core.bootstrap.configuration.mapper.MyObjectMapper;
import net.pool.station.core.bootstrap.enums.EResourceStatus;
import net.pool.station.core.bootstrap.enums.EResourceType;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;

import java.util.List;

public record StationResourceRequest(
        Long areaId,
        String resourceCode,
        String resourceName,
        String typeCode,
        String typeName,
        String statusCode,
        String statusName,
        SpecsRequest specs
) {
    public StationResourceRequest {
        if (MyObjectUtils.isEmpty(typeCode)) {
            typeCode = EResourceType.PC.getCode();
            typeName = EResourceType.PC.getName();
        }

        if (MyObjectUtils.isEmpty(statusCode)) {
            statusCode = EResourceStatus.ENABLE.getCode();
            statusName = EResourceStatus.ENABLE.getName();
        }
    }

    @Builder
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
