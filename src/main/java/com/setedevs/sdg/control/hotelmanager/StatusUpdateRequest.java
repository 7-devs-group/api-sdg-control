package com.setedevs.sdg.control.hotelmanager;

import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(@NotNull Boolean active) {
}
