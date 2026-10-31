package com.setedevs.sdg.control.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class JwtFilterTest {
    private static final String SECRET = "01234567890123456789012345678901";

    @Test
    void rejectsTokenIssuedBeforeResetWhenAuthNowRequiresPasswordChange() throws Exception {
        UUID userId = UUID.randomUUID();
        String token = Jwts.builder().subject(userId.toString()).claim("typ", "access")
                .claim("role", "SDG_ADMIN").claim("password_change_required", false)
                .expiration(new Date(System.currentTimeMillis() + 60_000))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8))).compact();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Bearer " + token);
        MockHttpServletResponse response = new MockHttpServletResponse();
        try {
            new JwtFilter(SECRET, id -> true).doFilterInternal(request, response, new MockFilterChain());
            assertEquals(403, response.getStatus());
            assertNull(SecurityContextHolder.getContext().getAuthentication());
        } finally {
            SecurityContextHolder.clearContext();
        }
    }
}
