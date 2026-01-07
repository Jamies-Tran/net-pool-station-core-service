package net.pool.station.core.features.match.making.match.making.controller.models.resource;

public record MatchMakingResourceResponse(
        MatchMakingResourceResponseId id,
        String typeCode,
        String typeName,
        Integer price
) {
    public record MatchMakingResourceResponseId(
            Long matchMakingId,
            Long stationResourceId
    ) {}
}
