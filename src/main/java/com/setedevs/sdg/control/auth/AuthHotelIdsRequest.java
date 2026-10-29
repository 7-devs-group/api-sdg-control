package com.setedevs.sdg.control.auth;

import java.util.Set;
import java.util.UUID;

public record AuthHotelIdsRequest(Set<UUID> hotelIds) {
}
