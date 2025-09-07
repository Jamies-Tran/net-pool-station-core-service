package net.pool.station.core.bootstrap.rest.response;

import lombok.Builder;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record MyPageResponse<T>(
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
            Integer pageSize,
            Integer current,
            Long total
    ) {
        public static Meta of(Page<?> page) {
            return Meta.builder()
                    .pageSize(page.getSize())
                    .current(page.getNumber())
                    .total(page.getTotalElements())
                    .build();
        }
    }

    public static <T> MyPageResponse<T> success(Page<T> data) {
        return MyPageResponse.<T>builder()
                .data(data.getContent())
                .meta(Meta.of(data))
                .status(String.valueOf(HttpStatus.OK.value()))
                .success(true)
                .message("Dịch vụ đã được thực hiện")
                .responseAt(LocalDateTime.now())
                .build();
    }
}

