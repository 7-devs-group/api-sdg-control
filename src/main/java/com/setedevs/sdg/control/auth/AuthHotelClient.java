package com.setedevs.sdg.control.auth;

import com.setedevs.sdg.control.hotel.Hotel;
import com.setedevs.sdg.control.hotel.HotelStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public class AuthHotelClient {

    private final RestClient restClient;
    private final String integrationKey;

    public AuthHotelClient(
            RestClient.Builder restClientBuilder,
            @Value("${app.auth.base-url}") String authBaseUrl,
            @Value("${app.auth.integration-key}") String integrationKey
    ) {
        this.restClient = restClientBuilder.baseUrl(authBaseUrl).build();
        this.integrationKey = integrationKey;
    }

    public void synchronizeHotel(Hotel hotel) {
        restClient.post()
                .uri("/internal/auth/hotels")
                .header("X-SDG-Control-Key", integrationKey)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(new AuthHotelSyncRequest(hotel.getId(), hotel.getName(), hotel.getSlug(), hotel.getStatus() == HotelStatus.ACTIVE))
                .retrieve()
                .toBodilessEntity();
    }

    public void createMembership(String name, String email, String password, Hotel hotel, String role) {
        restClient.post()
                .uri("/internal/auth/hotels/memberships")
                .header("X-SDG-Control-Key", integrationKey)
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(new AuthMembershipSyncRequest(name, email, password, hotel.getId(), role))
                .retrieve()
                .toBodilessEntity();
    }

    public AuthPlatformUserResponse createPlatformUser(String name, String email, String password) {
        return restClient.post().uri("/internal/auth/platform-users").header("X-SDG-Control-Key", integrationKey)
                .body(new AuthPlatformUserRequest(name, email, password)).retrieve().body(AuthPlatformUserResponse.class);
    }

    public List<AuthPlatformUserResponse> listPlatformUsers() {
        return restClient.get().uri("/internal/auth/platform-users").header("X-SDG-Control-Key", integrationKey).retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public AuthPlatformUserResponse updatePlatformUserStatus(UUID id, boolean active) {
        return restClient.patch().uri("/internal/auth/platform-users/{id}/status", id).header("X-SDG-Control-Key", integrationKey)
                .body(new AuthStatusRequest(active)).retrieve().body(AuthPlatformUserResponse.class);
    }

    public void resetPlatformUserPassword(UUID id, String password) {
        restClient.post().uri("/internal/auth/platform-users/{id}/reset-password", id).header("X-SDG-Control-Key", integrationKey)
                .body(new AuthPasswordRequest(password)).retrieve().toBodilessEntity();
    }

    public AuthHotelManagerResponse createHotelManager(String name, String email, String password, Set<UUID> hotelIds) {
        return restClient.post().uri("/internal/auth/hotel-managers").header("X-SDG-Control-Key", integrationKey)
                .body(new AuthHotelManagerRequest(name, email, password, hotelIds)).retrieve().body(AuthHotelManagerResponse.class);
    }

    public List<AuthHotelManagerResponse> listHotelManagers() {
        return restClient.get().uri("/internal/auth/hotel-managers").header("X-SDG-Control-Key", integrationKey).retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public AuthHotelManagerResponse replaceManagerHotels(UUID id, Set<UUID> hotelIds) {
        return restClient.put().uri("/internal/auth/hotel-managers/{id}/hotels", id).header("X-SDG-Control-Key", integrationKey)
                .body(new AuthHotelIdsRequest(hotelIds)).retrieve().body(AuthHotelManagerResponse.class);
    }

    public AuthHotelManagerResponse updateManagerStatus(UUID id, boolean active) {
        return restClient.patch().uri("/internal/auth/hotel-managers/{id}/status", id).header("X-SDG-Control-Key", integrationKey)
                .body(new AuthStatusRequest(active)).retrieve().body(AuthHotelManagerResponse.class);
    }

    public void resetManagerPassword(UUID id, String password) {
        restClient.post().uri("/internal/auth/hotel-managers/{id}/reset-password", id).header("X-SDG-Control-Key", integrationKey)
                .body(new AuthPasswordRequest(password)).retrieve().toBodilessEntity();
    }

}
