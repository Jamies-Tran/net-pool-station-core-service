package net.pool.station.core.bootstrap.rest.response;

import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MyListResponse<T>(
        List<T> data,
        Meta meta,
        String status,
        Boolean success,
        String errorCode,
        LocalDateTime responseAt,
        String message
) {
    @Builder
    public record Meta(
            Integer total
    ) {
        public static Meta of(List<?> list) {
            return Meta.builder()
                    .total(list.size())
                    .build();
        }
    }

    public static <T> MyListResponse<T> success(List<T> data) {
        return MyListResponse.<T>builder()
                .data(data)
                .meta(Meta.of(data))
                .status(String.valueOf(HttpStatus.OK.value()))
                .success(true)
                .message("Dịch vụ đã được thực hiện")
                .responseAt(LocalDateTime.now())
                .build();
    }
}

