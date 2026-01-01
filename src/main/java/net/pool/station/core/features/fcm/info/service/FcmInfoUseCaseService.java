package net.pool.station.core.features.fcm.info.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.fcm.info.FcmInfo;
import net.pool.station.core.domain.fcm.info.FcmInfoUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FcmInfoUseCaseService implements FcmInfoUseCase {
    FcmInfoCommandService commandService;

    FcmInfoQueryService queryService;

    @Override
    @Transactional
    public void save(FcmInfo fcmInfo) {
        commandService.save(fcmInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FcmInfo> findAllByAccountId(DomainKey<Long> accountId) {
        return queryService.findByAccountId(accountId.value());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FcmInfo> findAllByAccountIdIn(List<Long> accountIds) {
        return queryService.findAllByAccountIdIn(accountIds);
    }
}
