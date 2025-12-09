package net.pool.station.core.features.station.resource.specs.controller.models.bt;

import lombok.Builder;

@Builder
public record BilliardTableSpecResponse(
        String btTableDetail,
        String btCueDetail,
        String btBallDetail
) {
}
