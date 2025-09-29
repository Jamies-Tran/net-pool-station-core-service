package net.pool.station.core.features.email.verification.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.domain.DomainKey;
import net.pool.station.core.domain.email.verification.EmailVerification;
import net.pool.station.core.domain.email.verification.EmailVerificationUseCase;
import net.pool.station.core.features.email.verification.controller.models.EmailVerificationRequest;
import net.pool.station.core.features.email.verification.controller.models.VerificationRequest;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailVerificationPubController implements EmailVerificationPubApi {
    EmailVerificationUseCase useCase;

    @Override
    public MyValueResponse<?> save(EmailVerificationRequest request) {
        useCase.save(EmailVerification.builder()
                .email(request.email())
                .build());

        return MyValueResponse.successNoData();
    }

    @Override
    public MyValueResponse<?> verify(VerificationRequest request) {
        useCase.verify(DomainKey.of(request.verificationCode()));

        return MyValueResponse.successNoData();
    }
}
