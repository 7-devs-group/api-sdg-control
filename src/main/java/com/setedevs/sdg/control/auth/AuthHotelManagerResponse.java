package com.setedevs.sdg.control.auth;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record AuthHotelManagerResponse(UUID id, String name, String email, boolean active, Instant createdAt, List<AuthManagerHotelResponse> hotels) {
}
