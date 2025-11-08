package net.pool.station.core.bootstrap.utils;

import net.pool.station.core.bootstrap.configuration.mapper.MyObjectMapper;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MyPaymentEncryptionUtils {
    private static String secretKey;

    @Value("${environment.payOs.checksum-key}")
    private void setSecretKey(String secretKey) {
        MyPaymentEncryptionUtils.secretKey = secretKey;
    }

    public static String encrypt(String data) {
        try {
            String raw = convertToRawString(data);
            return new HmacUtils("HmacSHA256", secretKey).hmacHex(raw);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private static String convertToRawString(String data) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> map = MyObjectMapper.convertFromStringToMap(data)
                .entrySet().stream()
                .filter(entry -> MyObjectUtils.isNotEmpty(entry.getValue()))
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue,
                        LinkedHashMap::new
                ));
        Iterator<String> keys = map.keySet().stream().toList().iterator();
        while (keys.hasNext()) {
            String key = keys.next();
            Object value = map.get(key);
            if (value instanceof Collection) {
                value = MyObjectMapper.convertFromObjectToString(value);
            }
            stringBuilder
                    .append(key)
                    .append("=")
                    .append(value);
            if (keys.hasNext()) {
                stringBuilder.append("&");
            }
        }
        return stringBuilder.toString();
    }
}
