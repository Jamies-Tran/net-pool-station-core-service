package net.pool.station.core.features.login.info.controller.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record LoginInfoRequest(
        @NotNull(message = "Vui lập nhập email")
        @Pattern(message = "Email không hợp lệ",
                regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        String email,

        @NotNull(message = "Vui lòng nhập mật khẩu")
        String password
) {
}
