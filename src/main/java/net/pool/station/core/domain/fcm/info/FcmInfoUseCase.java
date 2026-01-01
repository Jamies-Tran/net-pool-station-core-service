package net.pool.station.core.domain.fcm.info;

import net.pool.station.core.domain.DomainKey;

import java.util.List;

public interface FcmInfoUseCase {
    void save(FcmInfo fcmInfo);

    List<FcmInfo> findAllByAccountId(DomainKey<Long> accountId);

    List<FcmInfo> findAllByAccountIdIn(List<Long> accountIds);
}
