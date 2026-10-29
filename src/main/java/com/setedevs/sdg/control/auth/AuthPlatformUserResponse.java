package com.setedevs.sdg.control.auth;

import java.time.Instant;
import java.util.UUID;

public record AuthPlatformUserResponse(UUID id, String name, String email, String role, boolean active, Instant createdAt, Instant updatedAt) {
}
