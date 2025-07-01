package ru.tdd.bookchange.application.services.imp;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tdd.bookchange.application.dto.JwtTokenResponse;
import ru.tdd.bookchange.application.dto.SignIn;
import ru.tdd.bookchange.application.dto.SignUp;
import ru.tdd.bookchange.application.exceptions.AlreadyExistException;
import ru.tdd.bookchange.application.exceptions.AuthorizationException;
import ru.tdd.bookchange.application.exceptions.NotFoundException;
import ru.tdd.bookchange.application.services.AuthService;
import ru.tdd.bookchange.application.services.JwtTokenService;
import ru.tdd.bookchange.application.utils.TextUtils;
import ru.tdd.bookchange.domen.entities.AppUser;
import ru.tdd.bookchange.domen.entities.Email;
import ru.tdd.bookchange.domen.enums.users.Role;
import ru.tdd.bookchange.domen.enums.users.UserState;
import ru.tdd.bookchange.domen.repositories.UserRepository;
import ru.tdd.bookchange.domen.specifications.UserCoreSpecification;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 */
@Service
public class AuthServiceImp implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public AuthServiceImp(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtTokenService jwtTokenService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
    }

    @Override
    public JwtTokenResponse signIn(@Valid SignIn signIn) {
        String username = signIn.getUsername();
        String password = signIn.getPassword();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (AuthenticationException e) {
            throw new AuthorizationException();
        }
        AppUser user = userRepository.findOne(UserCoreSpecification.byUsername(username))
                .orElseThrow(() -> new NotFoundException(String.format("Пользователь: \"%s\" не найден", username)));

        user.setLastDateOnline(LocalDateTime.now());
        userRepository.save(user);

        return new JwtTokenResponse(jwtTokenService.generateToken(user));
    }

    @Override
    public JwtTokenResponse signUp(@Valid SignUp signUp) {
        String username = signUp.getUsername();
        String password = signUp.getPassword();
        String confirmPassword = signUp.getConfirmPassword();
        String email = signUp.getEmail();
        String telegramUsername = signUp.getTelegramUsername();

        if (!Objects.equals(password, confirmPassword)) {
            throw new AuthorizationException("Пароль не подтвержден");
        }

        if (userRepository.exists(UserCoreSpecification.byUsername(username))) {
            throw new AlreadyExistException(String.format("Пользователь: \"%s\" уже создан", username));
        }

        if (TextUtils.isNotEmpty(email) && userRepository.exists(UserCoreSpecification.byEmail(email))) {
            throw new AlreadyExistException(
                    String.format(
                            "Пользователь с указанным электронным адресом: \"%s\" уже создан",
                            email
                    )
            );
        }

        if (
                TextUtils.isNotEmpty(telegramUsername) &&
                        userRepository.exists(UserCoreSpecification.byTelegramUsername(email))
        ) {
            throw new AlreadyExistException(
                    String.format(
                            "Пользователь с указанным именем в телеграмме; \"%s\" уже создан",
                            telegramUsername
                    )
            );
        }

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);

        AppUser user = AppUser.builder()
                .username(username)
                .state(UserState.ACTIVE)
                .password(passwordEncoder.encode(password))
                .telegramUsername(telegramUsername)
                .email(new Email(email))
                .lastDateOnline(LocalDateTime.now())
                .roles(roles)
                .build();

        userRepository.save(user);

        return new JwtTokenResponse(jwtTokenService.generateToken(user));
    }
}
