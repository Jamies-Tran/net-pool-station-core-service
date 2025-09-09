package net.pool.station.core.features.email.verification.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.domain.email.verification.EmailVerification;
import net.pool.station.core.features.email.verification.repository.database.EmailVerificationMapper;
import net.pool.station.core.features.email.verification.repository.database.EmailVerificationRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailVerificationQueryService {
    EmailVerificationRepository repository;

    EmailVerificationMapper mapper;

    protected Optional<EmailVerification> findByVerificationCode(String verificationCode) {
        return repository.findByVerificationCode(verificationCode)
                .map(mapper::toDto);
    }
}
