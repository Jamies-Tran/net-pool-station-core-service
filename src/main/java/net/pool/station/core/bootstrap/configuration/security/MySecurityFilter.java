package net.pool.station.core.bootstrap.configuration.security;


import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.pool.station.core.bootstrap.configuration.handler.exception.EErrorCode;
import net.pool.station.core.bootstrap.configuration.handler.exception.MyAuthenticationException;
import net.pool.station.core.bootstrap.configuration.mapper.MyObjectMapper;
import net.pool.station.core.bootstrap.rest.response.MyValueResponse;
import net.pool.station.core.bootstrap.utils.MyTokenUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MySecurityFilter extends OncePerRequestFilter {
    MyTokenUtils tokenUtil;

    MyUserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        try {

            String token = tokenUtil.getTokenFromRequest(request);
            if(Optional.ofNullable(token).isPresent()) {
                tokenUtil.validateToken(token);
                String identification = tokenUtil.getUserIdentifyFromToken(token);

                UserDetails userDetails = userDetailsService.loadUserByUsername(identification);
                UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken.authenticated(
                        userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(authToken);
                SecurityContextHolder.setContext(securityContext);
                log.info("Authenticated with token: {}", token);
            }

            filterChain.doFilter(requestWrapper , response);

        } catch (ExpiredJwtException e) {
            log.error("[{}-doFilterInternal] Token không hợp lệ", this.getClass().getSimpleName());
            MyValueResponse<?> errorResponse = MyValueResponse
                    .error("Phiên đăng nhập đã hết hạn.",
                            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                            EErrorCode.TOKEN_EXPIRED.getCode());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(MyObjectMapper.convertDataToJsonString(errorResponse));
        } catch (MyAuthenticationException e) {
            MyValueResponse<?> errorResponse = MyValueResponse
                    .error(e.getMessage(),
                            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                            EErrorCode.NO_AUTHORITY.getCode());
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            response.getWriter().write(MyObjectMapper.convertDataToJsonString(errorResponse));
        }  catch (RuntimeException e) {
            log.error("[{}-doFilterInternal] Xác thực that bại", this.getClass().getSimpleName());
            MyValueResponse<?> errorResponse = MyValueResponse
                    .error("Lỗi xác thực",
                            HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                            EErrorCode.AUTHORIZE_EXCEPTION.getCode());
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            response.getWriter().write(MyObjectMapper.convertDataToJsonString(errorResponse));
        } finally {
            String authorization = requestWrapper.getHeader("Authorization");
            String method = request.getMethod();
            String path = request.getRequestURI();
            String body = new String(requestWrapper.getContentAsByteArray(), StandardCharsets.UTF_8);
            List<String> paramList = requestWrapper.getParameterMap().entrySet().stream()
                    .map(x -> "%s - %s".formatted(x.getKey(), String.join(", ", x.getValue()))).toList();
            String param = String.join(",", paramList);
            String requestAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
            log.info("\n{\n Request logging: " +
                    "\n Authorization: [{}]" +
                    "\n Method: [{}]  " +
                    "\n Path: [{}]" +
                    "\n Param: [{}]" +
                    "\n Body: [{}]" +
                    "\n time: [{}]\n}", authorization, method, path, param, body, requestAt);
        }
    }
}
