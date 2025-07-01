package ru.tdd.bookchange.infrastructure.rest_controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tdd.bookchange.application.dto.JwtTokenResponse;
import ru.tdd.bookchange.application.dto.SignIn;
import ru.tdd.bookchange.application.dto.SignUp;
import ru.tdd.bookchange.application.services.AuthService;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 * Контроллер для авторизации и регистрации пользователей
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Запрос на авторизацию пользователей
     */
    @PostMapping("/sign-in")
    public ResponseEntity<JwtTokenResponse> signIn(@Valid @RequestBody SignIn signIn) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.signIn(signIn));
    }

    /**
     * Регистрация пользователей
     */
    @PostMapping("/sign-up")
    public ResponseEntity<JwtTokenResponse> signUp(@Valid @RequestBody SignUp signUp) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signUp(signUp));
    }
}
