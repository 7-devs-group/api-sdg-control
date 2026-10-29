package com.setedevs.sdg.control.hotelmanager;

import java.util.UUID;

public record ManagerHotelResponse(UUID id, String name, String slug, boolean active) {
}
