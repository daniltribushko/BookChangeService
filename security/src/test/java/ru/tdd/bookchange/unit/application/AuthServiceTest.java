package ru.tdd.bookchange.unit.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.tdd.bookchange.application.dto.JwtTokenResponse;
import ru.tdd.bookchange.application.dto.SignUp;
import ru.tdd.bookchange.application.exceptions.AlreadyExistException;
import ru.tdd.bookchange.application.exceptions.AuthorizationException;
import ru.tdd.bookchange.application.services.JwtTokenService;
import ru.tdd.bookchange.application.services.imp.AuthServiceImp;
import ru.tdd.bookchange.application.utils.TextUtils;
import ru.tdd.bookchange.domen.repositories.UserRepository;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenService jwtTokenService;

    @InjectMocks
    private AuthServiceImp authService;

    @Test
    void signUpSuccess() {
        SignUp signUp = new SignUp(
                "test_sign_up",
                "123",
                "123",
                "test_email@gmail.com",
                "test_tg"
        );

        Mockito.when(userRepository.exists(any(Specification.class))).thenReturn(false);
        Mockito.when(jwtTokenService.generateToken(any())).thenReturn("test_token");
        JwtTokenResponse tokenResponse = authService.signUp(signUp);

        Assertions.assertNotNull(tokenResponse);
        Assertions.assertTrue(TextUtils.isNotEmpty(tokenResponse.getToken()));
    }

    @Test
    void signUpFail() {
        SignUp signUp1 = new SignUp(
                "failed_user",
                "123",
                "1234",
                "test_email@gmail.com",
                "test_tg"
        );

        AuthorizationException actualException1 = Assertions.assertThrows(
                AuthorizationException.class,
                () -> authService.signUp(signUp1)
        );

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED.value(), actualException1.getCode());
        Assertions.assertEquals("Пароль не подтвержден", actualException1.getMessage());

        SignUp signUp2 = new SignUp(
          "already_exist_user",
          "123",
          "123",
          "email1",
          "tg"
        );

        Mockito.when(userRepository.exists(any(Specification.class))).thenReturn(true);

        AlreadyExistException actualException2 = Assertions.assertThrows(
                AlreadyExistException.class,
                () -> authService.signUp(signUp2)
        );

        Assertions.assertEquals(HttpStatus.CONFLICT.value(), actualException2.getCode());
        Assertions.assertTrue(actualException2.getMessage().contains("уже создан"));
    }
}