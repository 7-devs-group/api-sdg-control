package com.setedevs.sdg.control.platformuser;

import jakarta.validation.constraints.NotNull;

public record StatusUpdateRequest(@NotNull Boolean active) {
}
