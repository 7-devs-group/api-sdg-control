package com.setedevs.sdg.control.auth;

import java.util.UUID;

public record AuthHotelSyncRequest(UUID hotelId, String slug, boolean active) {
}
