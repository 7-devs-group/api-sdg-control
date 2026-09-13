package com.setedevs.sdg.control.hotel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateMembershipRequest(
        @NotBlank @Size(max = 160) String name,
        @Email @NotBlank String email,
        @NotBlank @Size(min = 12, max = 128) String password,
        @NotBlank @Size(max = 80) String role
) {
}
