package com.setedevs.sdg.control.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
class JwtFilter extends OncePerRequestFilter {
    private final SecretKey key;
    JwtFilter(@Value("${app.jwt.secret}") String secret) {
        if (secret == null || secret.getBytes(StandardCharsets.UTF_8).length < 32) throw new IllegalStateException("JWT_SECRET must contain at least 32 bytes");
        key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    @Override protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException {
        String value = req.getHeader("Authorization");
        if (value != null && value.startsWith("Bearer ")) {
            try {
                Claims c = Jwts.parser().verifyWith(key).build().parseSignedClaims(value.substring(7)).getPayload();
                if (!"access".equals(c.get("typ", String.class))) throw new IllegalArgumentException();
                String role = c.get("role", String.class);
                var auth = new UsernamePasswordAuthenticationToken(c.getSubject(), null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));
                auth.setDetails(c);
                org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (RuntimeException e) { org.springframework.security.core.context.SecurityContextHolder.clearContext(); }
        }
        chain.doFilter(req, res);
    }
}

@Configuration
class SecurityConfiguration {
    @Bean SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter filter) throws Exception {
        return http.csrf(c -> c.disable()).sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(e -> e.authenticationEntryPoint((req, res, ex) -> res.sendError(401)))
                .authorizeHttpRequests(a -> a.requestMatchers("/actuator/health", "/api/health", "/v3/api-docs/**", "/swagger-ui/**").permitAll().anyRequest().hasAnyRole("HOTEL_ADMIN", "SDG_ADMIN"))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class).build();
    }
}
