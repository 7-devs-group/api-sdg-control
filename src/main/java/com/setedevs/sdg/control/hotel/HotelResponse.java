package com.setedevs.sdg.control.hotel;

import java.util.UUID;

public record HotelResponse(UUID id, String name, String slug, HotelStatus status) {

    static HotelResponse from(Hotel hotel) {
        return new HotelResponse(hotel.getId(), hotel.getName(), hotel.getSlug(), hotel.getStatus());
    }
}
