package net.pool.station.core.features.fcm.info.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.fcm.info.FcmInfo;
import net.pool.station.core.features.fcm.info.repository.database.FcmInfoEntity;
import net.pool.station.core.features.fcm.info.repository.database.FcmInfoMapper;
import net.pool.station.core.features.fcm.info.repository.database.FcmInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FcmInfoQueryService {
    FcmInfoRepository repository;

    FcmInfoMapper mapper;

    protected List<FcmInfo> findByAccountId(Long accountId) {
        List<FcmInfoEntity> fcmInfos = repository.findAllByAccountId(accountId);

        return mapper.toDto(fcmInfos);
    }

    protected List<FcmInfo> findAllByAccountIdIn(List<Long> accountIds) {
        List<FcmInfoEntity> fcmInfos = repository.findAllByAccountIdIn(accountIds);

        return mapper.toDto(fcmInfos);
    }
}
