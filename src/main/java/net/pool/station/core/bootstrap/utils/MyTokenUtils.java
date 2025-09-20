package net.pool.station.core.bootstrap.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MyTokenUtils {

    @Value("${environment.accessToken.secretKey}")
    String secretKey;

    @Value("${environment.accessToken.expired}")
    Integer accessExpiredDuration;

    @Value("${environment.refreshToken.expired}")
    Integer refreshDuration;

    public String bearerToken(String token) {
        return "Bearer %s".formatted(token);
    }

    public String generateAccessToken(String identification) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .subject(identification)
                .expiration(accessTokenExpiredAt())
                .issuedAt(new Date())
                .compact();

    }

    public String generateRefreshToken(String identification) {
        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .subject(identification)
                .expiration(refreshTokenExpiredAt())
                .issuedAt(new Date())
                .compact();

    }

    public Date accessTokenExpiredAt() {
        return Date.from(Instant.now().plus(accessExpiredDuration, ChronoUnit.MINUTES));
    }

    public Date refreshTokenExpiredAt() {
        return Date.from(Instant.now().plus(refreshDuration, ChronoUnit.DAYS));
    }

    public LocalDateTime convertRefreshExpiredAt() {
        return refreshTokenExpiredAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public LocalDateTime convertAccessExpiredAt() {
        return accessTokenExpiredAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public String getIdentityFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(StringUtils.hasText(token) && token.startsWith("Bearer")) {

            return getUserIdentifyFromToken(token.substring(7));
        }

        return null;
    }

    public String getUserIdentifyFromToken(String token) {
        return Jwts.parser()
                .verifyWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }

    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(new SecretKeySpec(secretKey.getBytes(), SignatureAlgorithm.HS256.getJcaName()))
                    .build()
                    .parseSignedClaims(token);
        } catch (ExpiredJwtException _) {
        }

    }
}
