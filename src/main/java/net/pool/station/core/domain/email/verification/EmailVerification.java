package net.pool.station.core.domain.email.verification;

import lombok.Builder;
import net.pool.station.core.bootstrap.configuration.common.EnvironmentVariable;

import java.util.Optional;

@Builder
public record EmailVerification(
        Long emailVerificationId,
        String email,
        String verificationCode
) {
    public EmailVerification {
        verificationCode = Optional.ofNullable(verificationCode)
                .orElse(EnvironmentVariable.generateRandomCode());
    }
}
