package net.pool.station.core.bootstrap.configuration.feign;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


public class PayOsFeignConfig {
    @Value("${environment.payOs.client-id}")
    String clientId;

    @Value("${environment.payOs.api-key}")
    String apiKey;


    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("x-client-id", clientId);
            requestTemplate.header("x-api-key", apiKey);
        };
    }
}
