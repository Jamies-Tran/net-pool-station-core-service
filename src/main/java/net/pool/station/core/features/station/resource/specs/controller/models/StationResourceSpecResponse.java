package net.pool.station.core.features.station.resource.specs.controller.models;

public record StationResourceSpecResponse(
        Long stationResourceSpecId,

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

        String csRefreshRate
) {
}
