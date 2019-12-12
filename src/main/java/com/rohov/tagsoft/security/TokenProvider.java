package com.rohov.tagsoft.security;

import com.rohov.tagsoft.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenProvider {

    @Value(value = "${spring.security.secret}")
    String secretKey;

    final String TOKEN_PREFIX = "Bearer ";
    final String TOKEN_TYPE = "JWT";
    final String TOKEN_ISSUER = "secure-api";
    final String TOKEN_AUDIENCE = "secure-api";

    final Long tokenExpiringTime = 3600000L;

    public String createToken(User user) {
        byte[] signKey = secretKey.getBytes();

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(signKey), SignatureAlgorithm.HS512)
                .setHeaderParam("type", TOKEN_TYPE)
                .setIssuer(TOKEN_ISSUER)
                .setAudience(TOKEN_AUDIENCE)
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiringTime))
                .compact();
    }
}
