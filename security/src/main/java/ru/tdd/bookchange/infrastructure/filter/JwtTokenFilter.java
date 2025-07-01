package ru.tdd.bookchange.infrastructure.filter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.tdd.bookchange.application.services.JwtTokenService;
import ru.tdd.bookchange.application.utils.TextUtils;
import ru.tdd.bookchange.domen.entities.AppUser;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

/**
 * @author Tribushko Danil
 * @since 22.06.2025
 * Фильтер для jwt токенов
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenService jwtTokenService;
    private final UserDetailsService userDetailsService;
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer";

    @Autowired
    public JwtTokenFilter(JwtTokenService jwtTokenService, UserDetailsService userDetailsService) {
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        if (
                authHeader != null && authHeader.startsWith(BEARER_PREFIX)
        ) {
            String token = authHeader.substring(BEARER_PREFIX.length() + 1);
            if (TextUtils.isNotEmpty(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
                Claims claims = jwtTokenService.parseToken(token);
                String username = claims.getSubject();
                AppUser user = (AppUser) userDetailsService.loadUserByUsername(username);

                if (jwtTokenService.validateToken(token, user)) {
                    SecurityContext context = SecurityContextHolder.createEmptyContext();
                    context.setAuthentication(
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    user.getAuthorities()
                            )
                    );
                    SecurityContextHolder.setContext(context);
                }
            } else {
                throw new AuthenticationException("Токен не валидный");
            }
        } else {
            doFilter(request, response, filterChain);
        }
    }
}
