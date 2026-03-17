package com.epam.filter;

import com.epam.model.SystemUser;
import com.epam.repository.SystemUserRepository;
import com.epam.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * JWT authentication filter executed once per request.
 * Extracts the Bearer token, validates it, loads the user, and populates the SecurityContext
 * with the user's identity and role-based authority.
 */
@Slf4j
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final SystemUserRepository systemUserRepository;
    private final JwtService jwtService;

    public JwtAuthFilter(SystemUserRepository systemUserRepository, JwtService jwtService) {
        this.systemUserRepository = systemUserRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            log.debug("Incoming request: {} {}", request.getMethod(), request.getRequestURI());

            final String authHeader = request.getHeader(AUTHORIZATION_HEADER);
            if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(BEARER_PREFIX.length());
            String username = jwtService.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                SystemUser user = systemUserRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException(
                                "User not found for email: " + username));

                // Build authorities from the role claim in the JWT token
                String role = jwtService.getRoleFromToken(token);
                var authorities = List.of(new SimpleGrantedAuthority(role));

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(user, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            log.error("JWT authentication failed: {}", ex.getMessage());
            filterChain.doFilter(request, response);
        }
    }
}
