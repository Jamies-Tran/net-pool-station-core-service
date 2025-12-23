package net.pool.station.core.bootstrap.rest.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Builder
public record MyMapResponse<K, V>(
        Map<K, V> data,
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
        public static Meta of(Map<?, ?> list) {
            return Meta.builder()
                    .total(list.size())
                    .build();
        }
    }

    public static <K, V> MyMapResponse<K, V> success(Map<K, V> data) {
        return MyMapResponse.<K, V>builder()
                .data(data)
                .meta(Meta.of(data))
                .status(String.valueOf(HttpStatus.OK.value()))
                .success(true)
                .message("Dịch vụ đã được thực hiện")
                .responseAt(LocalDateTime.now())
                .build();
    }
}

