package net.pool.station.core.bootstrap.rest.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Builder
public record MyValueResponse<T> (
        T data,
        String status,
        Boolean success,
        String errorCode,
        LocalDateTime responseAt,
        String message
) {
    public static <T> MyValueResponse<T> success(T data) {
        return MyValueResponse.<T>builder()
                .data(data)
                .status(String.valueOf(HttpStatus.OK.value()))
                .success(true)
                .message("Dịch vụ đã được thực hiện")
                .responseAt(LocalDateTime.now())
                .build();
    }

    public static <T> MyValueResponse<T> successNoData() {
        return MyValueResponse.<T>builder()
                .data(null)
                .status(String.valueOf(HttpStatus.OK.value()))
                .success(true)
                .message("Dịch vụ đã được thực hiện")
                .responseAt(LocalDateTime.now())
                .build();
    }

    public static <T> MyValueResponse error(
            String status,
            String errorCode,
            String message
    ) {
        return MyValueResponse.builder()
                .status(status)
                .success(false)
                .message(message)
                .errorCode(errorCode)
                .responseAt(LocalDateTime.now())
                .build();
    }
}
