package net.pool.station.core.features.email.verification.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyResourceDuplicateException;
import net.pool.station.core.domain.email.verification.EmailVerification;
import net.pool.station.core.features.email.verification.repository.database.EmailVerificationMapper;
import net.pool.station.core.features.email.verification.repository.database.EmailVerificationRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailVerificationCommandService {
    EmailVerificationRepository repository;

    EmailVerificationMapper mapper;

    protected EmailVerification save(@NonNull EmailVerification emailVerification) {
        repository.findByVerificationCode(emailVerification.verificationCode())
                        .ifPresent(_ -> {
                            throw new MyResourceDuplicateException();
                        });
        return mapper.toDto(repository.save(mapper.toEntity(emailVerification)));
    }

    protected void delete(@NonNull Long emailVerificationId) {
        repository.deleteById(emailVerificationId);
    }
}
