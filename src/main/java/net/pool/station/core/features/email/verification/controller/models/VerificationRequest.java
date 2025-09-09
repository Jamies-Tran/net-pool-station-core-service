package net.pool.station.core.features.email.verification.controller.models;

import jakarta.validation.constraints.NotNull;

public record VerificationRequest(
        @NotNull(message = "Mã xác nhận không được bỏ trống")
        String verificationCode
) {
}
