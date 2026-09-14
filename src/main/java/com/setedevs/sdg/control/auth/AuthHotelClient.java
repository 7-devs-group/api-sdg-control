package com.setedevs.sdg.control.auth;

import com.setedevs.sdg.control.hotel.Hotel;
import com.setedevs.sdg.control.hotel.HotelStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

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
}
