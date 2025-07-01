package ru.tdd.bookchange.integrations;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.tdd.bookchange.TestContainersConfig;
import ru.tdd.bookchange.application.dto.JwtTokenResponse;
import ru.tdd.bookchange.application.dto.SignIn;
import ru.tdd.bookchange.application.dto.SignUp;
import ru.tdd.bookchange.application.exceptions.AlreadyExistException;
import ru.tdd.bookchange.application.exceptions.AuthorizationException;
import ru.tdd.bookchange.application.services.imp.AuthServiceImp;
import ru.tdd.bookchange.domen.repositories.UserRepository;
import ru.tdd.bookchange.domen.specifications.UserCoreSpecification;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Import({TestContainersConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {"jwt.token.secret=QAj5vEEEKKWhmUmxazQI9cyBzabrPwPU5svzYKIuRYw="})
class AuthServiceIntegrationTest {

    @Autowired
    private AuthServiceImp authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void shouldSuccessfullySignUpAndSignIn() {
        SignUp signUp = new SignUp(
                "testuser",
                "password123",
                "password123",
                "test@example.com",
                "testtelegram"
        );

        JwtTokenResponse signUpResponse = authService.signUp(signUp);
        assertNotNull(signUpResponse.getToken());

        assertTrue(userRepository.exists(UserCoreSpecification.byUsername("testuser")));

        SignIn signIn = new SignIn("testuser", "password123");
        JwtTokenResponse signInResponse = authService.signIn(signIn);

        assertNotNull(signInResponse.getToken());
    }

    @Test
    void shouldFailWhenSignUpWithExistingUsername() {

        SignUp firstSignUp = new SignUp(
                "existinguser",
                "password123",
                "password123",
                "first@example.com",
                null
        );
        authService.signUp(firstSignUp);
        
        SignUp secondSignUp = new SignUp(
                "existinguser",
                "password456",
                "password456",
                "second@example.com",
                null
        );

        assertThrows(AlreadyExistException.class, () -> authService.signUp(secondSignUp));
    }

    @Test
    void shouldFailWhenSignInWithWrongPassword() {
        SignUp signUp = new SignUp(
                "authuser",
                "correctpass",
                "correctpass",
                "auth@example.com",
                null
        );
        authService.signUp(signUp);

        SignIn signIn = new SignIn("authuser", "wrongpass");
        assertThrows(AuthorizationException.class, () -> authService.signIn(signIn));
    }

    @Test
    void shouldFailWhenPasswordNotConfirmed() {
        SignUp signUp = new SignUp(
                "newuser",
                "password123",
                "differentpassword",
                "new@example.com",
                null
        );

        assertThrows(AuthorizationException.class, () -> authService.signUp(signUp));
    }
}