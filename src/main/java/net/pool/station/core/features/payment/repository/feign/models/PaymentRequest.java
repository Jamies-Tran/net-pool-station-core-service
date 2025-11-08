package net.pool.station.core.features.payment.repository.feign.models;

import lombok.Builder;
import lombok.With;

import java.util.List;
import java.util.Map;

@Builder
public record PaymentRequest(
        Long orderCode,
        Integer amount,
        String description,
        String buyerName,
        String buyerPhone,
        String buyerEmail,
        List<ItemRequest> items,
        String cancelUrl,
        String returnUrl,
        @With String signature
) {
    @Builder
    public record ItemRequest (
            String name,
            Integer quantity,
            Integer price,
            String unit
    ) {}

    public Map<String, Object> generateRawSignature() {
        return Map.of(
                "amount", amount,
                "cancelUrl", cancelUrl,
                "description", description,
                "orderCode", orderCode,
                "returnUrl", returnUrl
        );
    }
}
