package net.pool.station.core.features.fcm.info.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.fcm.info.FcmInfo;
import net.pool.station.core.features.fcm.info.repository.database.FcmInfoMapper;
import net.pool.station.core.features.fcm.info.repository.database.FcmInfoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FcmInfoCommandService {
    FcmInfoRepository repository;

    FcmInfoMapper mapper;

    protected void save(FcmInfo fcmInfo) {
        repository.findByDeviceId(fcmInfo.deviceId()).ifPresentOrElse(
                foundFcmInfo -> {
                    mapper.update(foundFcmInfo, fcmInfo);
                    repository.save(foundFcmInfo);
                },
                () -> repository.save(mapper.toEntity(fcmInfo))
        );
    }
}
