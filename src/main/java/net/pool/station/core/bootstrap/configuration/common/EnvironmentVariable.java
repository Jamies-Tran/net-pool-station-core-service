package net.pool.station.core.bootstrap.configuration.common;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class EnvironmentVariable {
    public static String systemEmail;

    @Value("${spring.mail.username}")
    public void setSystemEmail(String systemEmail) {
        EnvironmentVariable.systemEmail = systemEmail;
    }

    public static String generateRandomCode() {
        Random random = new Random();
        int generatedCode = 100000 + random.nextInt(900000);
        return String.valueOf(generatedCode);
    }

    public static String generateRandomCode(String source, int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        String formatSource = source.replace(" ", "");
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(formatSource.length());
            sb.append(formatSource.charAt(index));
        }

        return sb.toString();
    }
}
