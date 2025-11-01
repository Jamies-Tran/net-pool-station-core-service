package net.pool.station.core.domain.station.resource.specs;

import lombok.With;

public record StationResourceSpec(
        Long stationResourceSpecId,

        @With
        Long stationResourceId,

        String pcCpu,

        String pcRam,

        String pcGpuModel,

        String pcGpuSerial,

        String pcGpuCapacity,

        String pcStorageName,

        String pcStorageVRam,

        String btTypeCode,

        String btTypeName,

        String btSurfaceTypeCode,

        String btSurfaceTypeName,

        String btClothTypeCode,

        String btClothTypeName,

        Double csScreenSize,

        String csResolution,

        String csRefreshRate,

        @With
        String typeCode,

        @With
        String typeName
) {
}
