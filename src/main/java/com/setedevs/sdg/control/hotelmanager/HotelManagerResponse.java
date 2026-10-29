package com.setedevs.sdg.control.hotelmanager;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record HotelManagerResponse(UUID id, String name, String email, boolean active, Instant createdAt, List<ManagerHotelResponse> hotels) {
}
