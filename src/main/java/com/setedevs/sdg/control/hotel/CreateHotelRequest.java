package com.setedevs.sdg.control.hotel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateHotelRequest(
        @NotBlank @Size(max = 160) String name,
        @NotBlank @Pattern(regexp = "[a-z0-9-]{3,80}") String slug
) {
}
