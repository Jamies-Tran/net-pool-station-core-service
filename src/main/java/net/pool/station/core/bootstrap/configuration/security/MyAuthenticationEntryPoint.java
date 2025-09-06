package net.pool.station.core.bootstrap.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.pool.station.core.bootstrap.configuration.handler.exception.EErrorCode;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.io.OutputStream;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    ObjectMapper objectMapper;


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        HttpStatus unauthorized = HttpStatus.FORBIDDEN;
        MyValueResponse<?> errorResponse = MyValueResponse
                .error("Không có quyền truy cập.",
                        HttpStatus.FORBIDDEN.getReasonPhrase(),
                        EErrorCode.NO_AUTHORITY.getCode());
        response.setContentType("application/json");
        response.setStatus(unauthorized.value());
        OutputStream os = response.getOutputStream();
        objectMapper.writeValue(os, errorResponse);

        os.flush();
    }
}
