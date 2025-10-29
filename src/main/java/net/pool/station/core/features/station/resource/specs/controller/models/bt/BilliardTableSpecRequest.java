package net.pool.station.core.features.station.resource.specs.controller.models.bt;

public record BilliardTableSpecRequest(
        String btTypeCode,

        String btTypeName,

        String btSurfaceTypeCode,

        String btSurfaceTypeName,

        String btClothTypeCode,

        String btClothTypeName
) {
}
