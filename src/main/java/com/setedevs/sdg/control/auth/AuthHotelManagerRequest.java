package com.setedevs.sdg.control.auth;

import java.util.Set;
import java.util.UUID;

public record AuthHotelManagerRequest(String name, String email, String password, Set<UUID> hotelIds) {
}
