package net.pool.station.core.features.station.resource.specs.controller.models.pc;

public record PCSpecsRequest (
        String pcCpu,

        String pcRam,

        String pcGpuModel,

        String pcGpuSerial,

        String pcGpuCapacity,

        String pcStorageName,

        String pcStorageVRam
) {


}
