package net.pool.station.core.features.payment.repository.feign.models;

import net.pool.station.core.bootstrap.configuration.mapper.MyObjectMapper;

import java.util.Map;

public record PaymentResponse(
        String bin,
        String accountNumber,
        String accountName,
        String currency,
        String paymentLinkId,
        Integer amount,
        String description,
        String orderCode,
        String status,
        String checkoutUrl,
        String qrCode
) {
    public Map<String, Object> generateRawSignature() {
        return MyObjectMapper.convertFromObjectToMap(this);
    }
}
