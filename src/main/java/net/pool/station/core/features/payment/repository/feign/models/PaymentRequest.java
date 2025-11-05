package net.pool.station.core.features.payment.repository.feign.models;

import net.pool.station.core.bootstrap.utils.MyHMacEncryptionUtils;

import java.util.List;

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
    public record ItemRequest (
            String name,
            Integer quantity,
            Integer price,
            String unit
    ) {}

    public String signature() {
        String raw = "amount=%s&cancelUrl=%s&description=%s&orderCode=%s&returnUrl=%s"
                .formatted(amount, cancelUrl, description, orderCode, returnUrl);
        return MyHMacEncryptionUtils.encrypt(raw);
    }
}
