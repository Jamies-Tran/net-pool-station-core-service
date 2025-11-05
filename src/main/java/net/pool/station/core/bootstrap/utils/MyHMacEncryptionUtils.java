package net.pool.station.core.bootstrap.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class MyHMacEncryptionUtils {
    private static String secretKey;

    @Value("${environment.payOs.checksum-key}")
    private void setSecretKey(String secretKey) {
        MyHMacEncryptionUtils.secretKey = secretKey;
    }

    public static String encrypt(String raw) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(raw.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
