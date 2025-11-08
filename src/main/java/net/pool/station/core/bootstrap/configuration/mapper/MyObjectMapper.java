package net.pool.station.core.bootstrap.configuration.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SuppressWarnings({"rawtypes", "unchecked"})
public class MyObjectMapper {
    static ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(ObjectMapper injectObjectMapper) {
        objectMapper = injectObjectMapper;
    }

    public static String convertFromObjectToString(Object data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("[{}-convertDataToJsonString] có lỗi xảy ra: {} ", MyObjectMapper.class.getSimpleName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Object> convertFromStringToMap(String object) {
        try {
            return objectMapper.readValue(object, new TypeReference<Map<String, Object>>(){});
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static Map<String, Object> convertFromObjectToMap(Object object) {
        try {
            return objectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public static <T> T convertFromStringToObject(String data, TypeReference<T> typeReference) {
        try {
            return objectMapper.convertValue(data, typeReference);
        } catch (Exception e) {
            log.error("[{}-convertObjectFromString] có lỗi xảy ra: {} ", MyObjectMapper.class.getSimpleName(), e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
