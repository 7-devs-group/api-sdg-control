package com.setedevs.sdg.control.hotelmanager;

import com.setedevs.sdg.control.auth.AuthHotelClient;
import com.setedevs.sdg.control.auth.AuthHotelManagerResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelManagerService {
    private final AuthHotelClient authClient;

    public HotelManagerService(AuthHotelClient authClient) { this.authClient = authClient; }

    public HotelManagerResponse create(CreateHotelManagerRequest request) {
        return toResponse(authClient.createHotelManager(request.name().trim(), request.email().trim(), request.password(), request.hotelIds()));
    }

    public List<HotelManagerResponse> list() { return authClient.listHotelManagers().stream().map(this::toResponse).toList(); }

    public HotelManagerResponse replaceHotels(UUID id, HotelIdsRequest request) {
        return toResponse(authClient.replaceManagerHotels(id, request.hotelIds()));
    }

    public HotelManagerResponse updateStatus(UUID id, boolean active) { return toResponse(authClient.updateManagerStatus(id, active)); }

    public void resetPassword(UUID id, String password) { authClient.resetManagerPassword(id, password); }

    private HotelManagerResponse toResponse(AuthHotelManagerResponse response) {
        return new HotelManagerResponse(response.id(), response.name(), response.email(), response.active(), response.createdAt(),
                response.hotels().stream().map(hotel -> new ManagerHotelResponse(hotel.id(), hotel.name(), hotel.slug(), hotel.active())).toList());
    }
}
