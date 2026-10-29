package com.setedevs.sdg.control.hotelmanager;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.UUID;

public record HotelIdsRequest(@NotEmpty Set<@NotNull UUID> hotelIds) {
}
