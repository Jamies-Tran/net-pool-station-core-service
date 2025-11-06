package net.pool.station.core.bootstrap.utils;

import net.pool.station.core.bootstrap.configuration.mapper.MyObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MyHMacEncryptionUtils {
    private static String secretKey;

    @Value("${environment.payOs.checksum-key}")
    private void setSecretKey(String secretKey) {
        MyHMacEncryptionUtils.secretKey = secretKey;
    }

    public static String encrypt(Object data) {
        try {
            String raw = convertToRawString(data);
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(raw.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    private static String convertToRawString(Object data) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> map = MyObjectMapper.convert(data)
                .entrySet().stream()
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
