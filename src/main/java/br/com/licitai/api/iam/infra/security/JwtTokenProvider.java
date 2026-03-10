package br.com.licitai.api.iam.infra.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Utilitário para geração e validação de tokens JWT (stateless)
 */
@Component
public class JwtTokenProvider {

    private final SecretKey key;
    private final long validityInMilliseconds;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration:3600000}") long validityInMilliseconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.validityInMilliseconds = validityInMilliseconds;
    }

    public void createTokenWithCookie(HttpServletResponse response, String userId, String accountId, String roles) {
        String token = createToken(userId, accountId, roles);
        addAuthCookie(response, token);
    }

    public void addAuthCookie(HttpServletResponse response, String jwt) {

        ResponseCookie cookie = ResponseCookie.from("auth_token", jwt)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .sameSite("Lax")      // Lax é um bom default
                .maxAge(60 * 60 * 24) // 1 dia
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }


    public String createToken(String userId, String accountId, String roles) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .subject(userId)
                .claim("accountId", accountId)
                .claim("roles", roles)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();

    }

    /**
     * Extrai userId do token
     */
    public String getUserId(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Extrai accountId do token
     */
    public String getAccountId(String token) {
        return (String) Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("accountId");
    }

    /**
     * Extrai roles do token
     */
    public String getRoles(String token) {
        return (String) Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("roles");
    }

    /**
     * Valida o token
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

