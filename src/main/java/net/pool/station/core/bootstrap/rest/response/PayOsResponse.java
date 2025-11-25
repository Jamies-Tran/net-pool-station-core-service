package net.pool.station.core.bootstrap.rest.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.With;
import net.pool.station.core.bootstrap.configuration.mapper.MyObjectMapper;
import net.pool.station.core.features.payment.repository.feign.models.PaymentResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public record PayOsResponse<T>(
        String code,
        String desc,
        T data,
        @With  String signature

) {
    public Map<String, Object> generateRawSignature() {
        if (data instanceof PaymentResponse paymentResponse) {
            Map<String, Object> map = new TreeMap<>();
            map.put("code", code);
            map.put("desc", desc);
            map.putAll(paymentResponse.generateRawSignature());
            return map;
        }

        return Map.of();
    }
}
