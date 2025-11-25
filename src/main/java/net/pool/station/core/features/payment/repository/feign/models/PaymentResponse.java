package net.pool.station.core.features.payment.repository.feign.models;

import net.pool.station.core.bootstrap.configuration.mapper.MyObjectMapper;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public record PaymentResponse(
        String bin,
        String accountNumber,
        String accountName,
        String currency,
        String paymentLinkId,
        Integer expiredAt,
        Integer amount,
        String description,
        String orderCode,
        String status,
        String checkoutUrl,
        String qrCode
) {
    public Map<String, Object> generateRawSignature() {
        Map<String, Object> rawSignature = new TreeMap<>();
        if (Objects.nonNull(bin)) {
            rawSignature.put("data.bin", bin);
        }
        if (Objects.nonNull(accountNumber)) {
            rawSignature.put("data.accountNumber", accountNumber);
        }
        if (Objects.nonNull(accountName)) {
            rawSignature.put("data.accountName", accountName);
        }
        if (Objects.nonNull(currency)) {
            rawSignature.put("data.currency", currency);
        }
        if (Objects.nonNull(paymentLinkId)) {
            rawSignature.put("data.paymentLinkId", paymentLinkId);
        }
        if (Objects.nonNull(expiredAt)) {
            rawSignature.put("data.expiredAt", expiredAt);
        }
        if (Objects.nonNull(amount)) {
            rawSignature.put("data.amount", amount);
        }
        if (Objects.nonNull(description)) {
            rawSignature.put("data.description", description);
        }
        if (Objects.nonNull(orderCode)) {
            rawSignature.put("data.orderCode", orderCode);
        }
        if (Objects.nonNull(status)) {
            rawSignature.put("data.status", status);
        }
        if (Objects.nonNull(checkoutUrl)) {
            rawSignature.put("data.checkoutUrl", checkoutUrl);
        }
        if (Objects.nonNull(qrCode)) {
            rawSignature.put("data.qrCode", qrCode);
        }

        return rawSignature;
    }

    private String normalize(String value) {
        return Normalizer.normalize(value.trim(), Normalizer.Form.NFKC);
    }
}
