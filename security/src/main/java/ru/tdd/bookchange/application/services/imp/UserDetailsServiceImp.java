package ru.tdd.bookchange.application.services.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.tdd.bookchange.application.exceptions.AuthorizationException;
import ru.tdd.bookchange.domen.repositories.UserRepository;
import ru.tdd.bookchange.domen.specifications.UserCoreSpecification;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 */
@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findOne(UserCoreSpecification.byUsername(username))
                .orElseThrow(AuthorizationException::new);
    }
}
