package net.pool.station.core.domain.email.verification;

import lombok.NonNull;
import net.pool.station.core.domain.DomainCode;

public interface EmailVerificationUseCase {
    void save(@NonNull EmailVerification emailVerification);

    void verify(@NonNull DomainCode<String> verificationCode);
}
