package net.pool.station.core.features.station.station.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.enums.ERole;
import net.pool.station.core.bootstrap.enums.EStationStatus;
import net.pool.station.core.bootstrap.utils.MyObjectUtils;
import net.pool.station.core.bootstrap.utils.MyRequestContext;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.logging.factory.LoggingFactory;
import net.pool.station.core.domain.login.info.LoginInfo;
import net.pool.station.core.domain.map.place.PlaceUseCase;
import net.pool.station.core.domain.map.place.detail.PlaceDetail;
import net.pool.station.core.domain.station.Station;
import net.pool.station.core.domain.station.StationCriteria;
import net.pool.station.core.domain.station.StationUseCase;
import net.pool.station.core.domain.station.account.StationAccount;
import net.pool.station.core.domain.station.account.StationAccountId;
import net.pool.station.core.domain.station.account.StationAccountUseCase;
import net.pool.station.core.domain.station.log.StationLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StationUseCaseService implements StationUseCase {
    StationCommandService commandService;

    StationQueryService queryService;

    LoggingFactory<StationLog> loggingService;

    StationAccountUseCase stationAccountUseCase;

    PlaceUseCase placeUseCase;

    @Override
    @Transactional
    public void save(Station station) {
        PlaceDetail.Result placeDetail = placeDetail(station.placeId());
        PlaceDetail.Result.Geometry.Location location = placeDetail.geometry().location();
        Long savedId = commandService.save(station
                .withLatitude(location.latitude())
                .withLongitude(location.longitude()));
        LoginInfo loginInfo = MyRequestContext.currentLoginInfo()
                .orElseThrow(MyAuthenticationException::new);
        if (MyObjectUtils.isNotEquals(loginInfo.roleCode(), ERole.STATION_OWNER.getCode())) {
            throw new MyAuthenticationException();
        }

        stationAccountUseCase.save(StationAccount.builder()
                .stationAccountId(StationAccountId.of(savedId, loginInfo.accountId()))
                .build());
        loggingService.log(StationLog.createSave(savedId));
    }

    PlaceDetail.Result placeDetail(String placeId) {
        return placeUseCase.findDetailByPlaceId(placeId)
                .orElse(PlaceDetail.defaultPlaceDetail().result());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Station> findById(DomainKey<Long> stationId) {
        return queryService.findById(stationId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Station> findAll(StationCriteria criteria, PageRequest pageRequest) {
        return queryService.findAll(criteria, pageRequest);
    }

    @Override
    @Transactional
    public void update(DomainKey<Long> stationId, Station station) {
        PlaceDetail.Result placeDetail = placeDetail(station.placeId());
        PlaceDetail.Result.Geometry.Location location = placeDetail.geometry().location();
        commandService.update(stationId.value(), station
                .withLatitude(location.latitude())
                .withLongitude(location.longitude()));

        loggingService.log(StationLog.createUpdate(stationId.value()));
    }

    @Override
    @Transactional
    public void delete(DomainKey<Long> stationId) {
        commandService.delete(stationId.value());
    }

    @Override
    @Transactional
    public void accept(DomainKey<Long> stationId) {
        commandService.updateStatus(stationId.value(), EStationStatus.ACTIVE, null);

        loggingService.log(StationLog.createAccept(stationId.value()));
    }

    @Override
    @Transactional
    public void reject(DomainKey<Long> stationId, String rejectReason) {
        commandService.updateStatus(stationId.value(), EStationStatus.REJECT, rejectReason);

        loggingService.log(StationLog.createReject(stationId.value(), rejectReason));
    }

    @Override
    @Transactional
    public void disable(DomainKey<Long> stationId) {
        commandService.updateStatus(stationId.value(), EStationStatus.INACTIVE, null);

        loggingService.log(StationLog.createInActive(stationId.value()));
    }

    @Override
    @Transactional
    public void enable(DomainKey<Long> stationId) {
        commandService.updateStatus(stationId.value(), EStationStatus.ACTIVE, null);

        loggingService.log(StationLog.createActive(stationId.value()));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<String> findAllStationProvince(String province, PageRequest pageRequest) {
        return queryService.findAllStationProvinces(province, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<String> findAllStationCommune(String commune, PageRequest pageRequest) {
        return queryService.findAllStationCommunes(commune, pageRequest);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<String> findAllStationDistrict(String district, PageRequest pageRequest) {
        return queryService.findAllStationDistricts(district, pageRequest);
    }
}
