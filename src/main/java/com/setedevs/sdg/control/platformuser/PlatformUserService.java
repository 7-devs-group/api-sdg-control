package com.setedevs.sdg.control.platformuser;

import com.setedevs.sdg.control.auth.AuthHotelClient;
import com.setedevs.sdg.control.auth.AuthPlatformUserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlatformUserService {
    private final AuthHotelClient authClient;

    public PlatformUserService(AuthHotelClient authClient) { this.authClient = authClient; }

    public PlatformUserResponse create(CreatePlatformUserRequest request) {
        return toResponse(authClient.createPlatformUser(request.name().trim(), request.email().trim(), request.password()));
    }

    public List<PlatformUserResponse> list() {
        return authClient.listPlatformUsers().stream().map(this::toResponse).toList();
    }

    public PlatformUserResponse updateStatus(UUID id, boolean active) {
        return toResponse(authClient.updatePlatformUserStatus(id, active));
    }

    public void resetPassword(UUID id, String password) { authClient.resetPlatformUserPassword(id, password); }

    private PlatformUserResponse toResponse(AuthPlatformUserResponse response) {
        return new PlatformUserResponse(response.id(), response.name(), response.email(), response.role(), response.active(), response.createdAt(), response.updatedAt());
    }
}
