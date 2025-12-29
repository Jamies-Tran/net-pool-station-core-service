package net.pool.station.core.features.match.making.resource.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.match.making.resource.MatchMakingResource;
import net.pool.station.core.domain.match.making.resource.MatchMakingResourceId;
import net.pool.station.core.features.match.making.resource.repository.database.MatchMakingResourceEntity;
import net.pool.station.core.features.match.making.resource.repository.database.MatchMakingResourceEntityId;
import net.pool.station.core.features.match.making.resource.repository.database.MatchMakingResourceMapper;
import net.pool.station.core.features.match.making.resource.repository.database.MatchMakingResourceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MatchMakingResourceCommandService {
    MatchMakingResourceRepository repository;

    MatchMakingResourceMapper mapper;

    protected void save(Long matchMakingId, List<MatchMakingResource> matchMakingResources) {
        List<MatchMakingResourceEntity> resources = matchMakingResources.stream()
                .map(matchMakingResource -> {
                    MatchMakingResourceId matchMakingResourceId = matchMakingResource.id()
                            .withMatchMakingId(matchMakingId);

                    return mapper.toEntity(matchMakingResource.withId(matchMakingResourceId));
                })
                .toList();

        repository.saveAll(resources);
    }

    protected void update(Long matchMakingId, List<MatchMakingResource> matchMakingResources) {
        repository.deleteAllByMatchMakingId(matchMakingId);
        List<MatchMakingResourceEntity> resources = matchMakingResources.stream()
                .map(matchMakingResource -> {
                    MatchMakingResourceId matchMakingResourceId = matchMakingResource.id()
                            .withMatchMakingId(matchMakingId);

                    return mapper.toEntity(matchMakingResource.withId(matchMakingResourceId));
                })
                .toList();

        repository.saveAll(resources);
    }
}
