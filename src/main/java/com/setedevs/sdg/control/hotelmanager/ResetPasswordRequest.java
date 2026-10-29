package com.setedevs.sdg.control.hotelmanager;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(@NotBlank @Size(min = 8, max = 200) String password) {
}
