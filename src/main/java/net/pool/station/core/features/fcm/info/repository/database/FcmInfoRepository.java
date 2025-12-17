package net.pool.station.core.features.fcm.info.repository.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FcmInfoRepository extends JpaRepository<FcmInfoEntity, Long> {
    Optional<FcmInfoEntity> findByDeviceId(String deviceId);

    List<FcmInfoEntity> findAllByAccountId(Long accountId);

    List<FcmInfoEntity> findAllByAccountIdIn(List<Long> accountIds);
}
