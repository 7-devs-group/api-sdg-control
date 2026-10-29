package com.setedevs.sdg.control.platformuser;

import java.time.Instant;
import java.util.UUID;

public record PlatformUserResponse(UUID id, String name, String email, String role, boolean active, Instant createdAt, Instant updatedAt) {
}
