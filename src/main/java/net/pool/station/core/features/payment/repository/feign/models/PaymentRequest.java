package net.pool.station.core.features.payment.repository.feign.models;

import lombok.Builder;
import net.pool.station.core.bootstrap.utils.MyHMacEncryptionUtils;

import java.util.List;

@Builder
public record PaymentRequest(
        String orderCode,
        Integer amount,
        String description,
        String buyerName,
        String buyerPhone,
        String buyerEmail,
        List<ItemRequest> items,
        String cancelUrl,
        String returnUrl,
        String signature
) {
    @Builder
    public record ItemRequest (
            String name,
            Integer quantity,
            Integer price,
            String unit
    ) {}

    public String signature() {
        return MyHMacEncryptionUtils.encrypt(this);
    }
}
