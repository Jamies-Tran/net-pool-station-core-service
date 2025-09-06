package net.pool.station.core.bootstrap.configuration.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MyObjectMapper {
    static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper injectObjectMapper) {
        objectMapper = injectObjectMapper;
    }

    public static String convertDataToJsonString(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("[{}-convertDataToJsonString] có lỗi xảy ra: {} ", MyObjectMapper.class.getSimpleName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
