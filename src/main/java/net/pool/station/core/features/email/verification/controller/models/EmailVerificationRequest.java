package net.pool.station.core.features.email.verification.controller.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EmailVerificationRequest(
        @NotNull(
                message = "Vui lập nhập email"
        )
        @Pattern(
                message = "Email không hợp lệ",
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
        )
        String email
) {
}
