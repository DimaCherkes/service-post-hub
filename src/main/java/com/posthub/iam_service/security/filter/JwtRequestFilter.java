package com.posthub.iam_service.security.filter;

import com.posthub.iam_service.model.constants.ApiErrorMessage;
import com.posthub.iam_service.security.JwtTokenProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String LOGIN_PATH = "/auth/login";
    private static final String REGISTER_PATH = "/auth/register";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain)
            throws ServletException, IOException {

        Optional<String> authHeader = Optional.ofNullable(request.getHeader(AUTHORIZATION_HEADER));

        if (authHeader.isPresent() && authHeader.get().startsWith(BEARER_PREFIX)) {
            String jwt = authHeader.get().substring(BEARER_PREFIX.length());

            try {
                jwtTokenProvider.validateToken(jwt);
                Optional<String> emailOpt = Optional.ofNullable(jwtTokenProvider.getEmail(jwt));
                emailOpt.ifPresent(email -> {
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        List<SimpleGrantedAuthority> authorities = jwtTokenProvider.getRoles(jwt).stream()
                                .map(SimpleGrantedAuthority::new)
                                .collect(Collectors.toList());

                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                authorities
                        );
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                });
            } catch (RuntimeException e) {
                throw e;
            }
        }
        filterChain.doFilter(request, response);
    }
}