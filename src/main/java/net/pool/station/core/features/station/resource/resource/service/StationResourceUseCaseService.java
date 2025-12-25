package net.pool.station.core.features.station.resource.resource.service;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceNotValid;
import net.pool.station.core.bootstrap.configuration.mapper.MyObjectMapper;
import net.pool.station.core.bootstrap.enums.EResourceStatus;
import net.pool.station.core.bootstrap.utils.MyAesEncryptionUtils;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.station.resource.Row;
import net.pool.station.core.domain.station.resource.StationResource;
import net.pool.station.core.domain.station.resource.StationResourceCriteria;
import net.pool.station.core.domain.station.resource.StationResourceUseCase;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpec;
import net.pool.station.core.domain.station.resource.specs.StationResourceSpecUseCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationResourceUseCaseService implements StationResourceUseCase {
    StationResourceCommandService commandService;

    StationResourceQueryService queryService;

    StationResourceSpecUseCase stationResourceSpecUseCase;

    @Override
    @Transactional
    public void save(StationResource stationResource) {
        StationResource savedResource = commandService.save(stationResource);
        if (MyObjectUtils.isNotEmpty(stationResource.spec())) {
            stationResourceSpecUseCase.save(stationResource.spec()
                    .withStationResourceId(savedResource.stationResourceId()));
        }
    }

    @Override
    @Transactional
    public void save(DomainKey<Long> areaId, List<StationResource> stationResources) {


        commandService.saveAll(areaId.value(), stationResources);
    }

    @Override
    @Transactional
    public void saveWithSocketToken(String token, StationResource stationResource) {
        String encryptedToken = MyAesEncryptionUtils.decrypt(token);
        StationResource.AreaId areaId = MyObjectMapper
                .convertFromStringToObject(encryptedToken, new TypeReference<StationResource.AreaId>() {});
        commandService.save(areaId.areaId(), stationResource);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StationResource> findAll(StationResourceCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<Row, List<StationResource>> findAllMapByRow(StationResourceCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest).stream()
                .collect(Collectors.groupingBy(s -> Row.builder()
                        .rowCode(s.rowCode())
                        .rowName(s.rowName())
                        .build(), Collectors.collectingAndThen(Collectors.toList(), values -> {
                            values.sort(Comparator.comparing(StationResource::displayOrder));
                            return values;
                        })));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<StationResource> findById(DomainKey<Long> stationResourceId) {
        StationResourceSpec spec = stationResourceSpecUseCase.findByStationResourceId(stationResourceId)
                .orElse(null);
        return queryService
                .findById(stationResourceId.value())
                .map(s -> s.withSpec(spec));
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> stationResourceId, StationResource stationResource) {
        commandService.update(stationResourceId.value(), stationResource);
    }

    @Override
    @Transactional
    public void enable(DomainKey<Long> stationResourceId) {
        commandService.updateStatus(stationResourceId.value(), EResourceStatus.ENABLE);
    }

    @Override
    @Transactional
    public void disable(DomainKey<Long> stationResourceId) {
        commandService.updateStatus(stationResourceId.value(), EResourceStatus.DISABLE);
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> stationResourceId) {
        commandService.delete(stationResourceId.value());
    }
}
