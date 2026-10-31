package com.setedevs.sdg.control.platformuser;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPasswordRequest(@NotBlank @Size(min = 12, max = 128) String password) {
}
