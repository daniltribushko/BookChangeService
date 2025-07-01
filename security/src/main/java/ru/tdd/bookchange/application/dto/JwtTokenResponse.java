package ru.tdd.bookchange.application.dto;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 * Dto ответа на получение jwt токена
 */
public class JwtTokenResponse {
    private String token;

    public JwtTokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
