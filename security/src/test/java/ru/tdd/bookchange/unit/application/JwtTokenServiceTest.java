package ru.tdd.bookchange.unit.application;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import ru.tdd.bookchange.TestContainersConfig;
import ru.tdd.bookchange.application.enums.Time;
import ru.tdd.bookchange.application.services.JwtTokenService;
import ru.tdd.bookchange.application.utils.TextUtils;
import ru.tdd.bookchange.domen.entities.AppUser;
import ru.tdd.bookchange.domen.enums.users.Role;

import java.util.Date;
import java.util.Set;

@SpringBootTest
@Import(TestContainersConfig.class)
@TestPropertySource(properties = {"jwt.token.secret=QAj5vEEEKKWhmUmxazQI9cyBzabrPwPU5svzYKIuRYw="})
class JwtTokenServiceTest {
    @Autowired
    private JwtTokenService jwtTokenService;

    @Test
    void generateTokenTest() {
        AppUser testUser = AppUser.builder()
                .username("user_test_jwt")
                .password("123")
                .roles(Set.of(Role.USER))
                .build();

        String token = jwtTokenService.generateToken(testUser);

        Assertions.assertTrue(TextUtils.isNotEmpty(token));
    }

    @Test
    void getClaimsTest() {
        Date expiredIssuedAt = new Date();
        Date expiredExpiresAt = new Date(expiredIssuedAt.getTime() + Time.DAY.getMilliseconds());

        AppUser testUser = AppUser.builder()
                .username("user_test_jwt")
                .password("123")
                .roles(Set.of(Role.USER))
                .build();

        String token = jwtTokenService.generateToken(testUser);
        Claims claims = jwtTokenService.parseToken(token);

        Assertions.assertNotNull(claims);

        Date actualIssuedAt = claims.getIssuedAt();
        Date actualExpiresAt = claims.getExpiration();

        Assertions.assertEquals(testUser.getUsername(), claims.getSubject());
        Assertions.assertEquals(expiredExpiresAt.getYear(), actualExpiresAt.getYear());
        Assertions.assertEquals(expiredExpiresAt.getMonth(), actualExpiresAt.getMonth());
        Assertions.assertEquals(expiredExpiresAt.getDay(), actualExpiresAt.getDay());
        Assertions.assertEquals(expiredExpiresAt.getHours(), actualExpiresAt.getHours());
        Assertions.assertEquals(expiredExpiresAt.getMinutes(), actualExpiresAt.getMinutes());

        Assertions.assertEquals(expiredIssuedAt.getYear(), actualIssuedAt.getYear());
        Assertions.assertEquals(expiredIssuedAt.getMonth(), actualIssuedAt.getMonth());
        Assertions.assertEquals(expiredIssuedAt.getDay(), actualIssuedAt.getDay());
        Assertions.assertEquals(expiredIssuedAt.getHours(), actualIssuedAt.getHours());
        Assertions.assertEquals(expiredIssuedAt.getMinutes(), actualIssuedAt.getMinutes());
    }

    @Test
    void validTokenTest() {
        AppUser testUser = AppUser.builder()
                .username("valid_token_test")
                .password("123")
                .roles(Set.of(Role.ADMIN))
                .build();
        String token = jwtTokenService.generateToken(testUser);
        String notValidToken = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJVU0VSIl0sInN1YiI6ImV4YW1wbGUiLCJpYXQiOjE3NTEzMDI3NzQsImV4cCI6MTc1MTM4OTE3NH0.HmF2VeUBP0RbWAMyR96TCMkJB7DuCVZ5au1VwoV9XQ4";

        Assertions.assertTrue(jwtTokenService.validateToken(token, testUser));
        Assertions.assertFalse(jwtTokenService.validateToken(notValidToken, testUser));
    }
}
