package net.pool.station.core.features.email.verification.controller;

import jakarta.validation.Valid;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.features.email.verification.controller.models.EmailVerificationRequest;
import net.pool.station.core.features.email.verification.controller.models.VerificationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/pub/email-verification")
public interface EmailVerificationPubApi {
    @PostMapping("/send")
    MyValueResponse<?> save(@RequestBody @Valid EmailVerificationRequest request);

    @PostMapping("/verify")
    MyValueResponse<?> verify(@RequestBody @Valid VerificationRequest request);
}
