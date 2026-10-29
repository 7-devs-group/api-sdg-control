package com.setedevs.sdg.control.security;

import io.jsonwebtoken.Claims;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PlatformAdminGuard {
    public void requirePlatformAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()
                || !authentication.getAuthorities().stream().anyMatch(authority -> "ROLE_SDG_ADMIN".equals(authority.getAuthority()))
                || !(authentication.getDetails() instanceof Claims claims)
                || !"PLATFORM".equals(claims.get("scope", String.class))) {
            throw new AccessDeniedException("Platform administrator access is required");
        }
    }
}
