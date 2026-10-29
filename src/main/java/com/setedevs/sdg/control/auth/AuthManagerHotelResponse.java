package com.setedevs.sdg.control.auth;

import java.util.UUID;

public record AuthManagerHotelResponse(UUID id, String name, String slug, boolean active) {
}
