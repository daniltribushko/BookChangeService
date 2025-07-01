package ru.tdd.bookchange.application.services.imp;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.tdd.bookchange.application.services.JwtTokenService;
import ru.tdd.bookchange.domen.entities.AppUser;
import ru.tdd.bookchange.domen.enums.users.Role;
import ru.tdd.bookchange.application.enums.Time;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 * Реализация сервиса по работе с jwt токенами
 */
@Service
public class JwtTokenServiceImp implements JwtTokenService {
    @Value("${jwt.token.secret}")
    private String secret;

    @Override
    public String generateToken(AppUser user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles().stream().map(Role::name).toList());
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + Time.DAY.getMilliseconds());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .signWith(getSecretKey())
                .issuedAt(issuedAt)
                .expiration(expiration)
                .compact();
    }

    @Override
    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public boolean validateToken(String token, AppUser user) {
        Claims claims = parseToken(token);
        return Objects.equals(user.getUsername(), claims.getSubject()) &&
                new Date().before(claims.getExpiration());
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }
}
