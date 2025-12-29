package net.pool.station.core.domain.match.making.resource;

import net.pool.station.core.domain.DomainKey;

import java.util.List;

public interface MatchMakingResourceUseCase {
    void save(DomainKey<Long> matchMakingId, List<MatchMakingResource> matchMakingResources);

    void update(DomainKey<Long> matchMakingId, List<MatchMakingResource> matchMakingResources);

    List<MatchMakingResource> findAllByMatchMakingId(DomainKey<Long> matchMakingId);
}
