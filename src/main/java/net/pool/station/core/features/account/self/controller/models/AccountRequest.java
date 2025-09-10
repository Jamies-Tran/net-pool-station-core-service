package net.pool.station.core.features.account.self.controller.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record AccountRequest(
        String avatar,
        @NotNull(
                message = "Vui lòng nhập username"
        )
        String username,
        @NotNull(
                message = "Vui lòng nhập mật khẩu"
        )
        String password,
        @Length(
                message = "Số CCCD phải có ít nhất 12 ký tự số",
                min = 12
        )
        String identification,
        @NotNull(
                message = "Vui lập nhập số điện thoại"
        )
        @Pattern(
                regexp = "^(?:\\+84|0)(3[2-9]|5[2689]|7[0-9]|8[1-9]|9[0-9])[0-9]{7}$",
                message = "Số điện thoại không hợp lệ"
        )
        String phone,
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
