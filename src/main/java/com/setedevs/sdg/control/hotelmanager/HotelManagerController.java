package com.setedevs.sdg.control.hotelmanager;

import com.setedevs.sdg.control.security.PlatformAdminGuard;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/control/hotel-managers")
public class HotelManagerController {
    private final HotelManagerService service;
    private final PlatformAdminGuard platformAdminGuard;

    public HotelManagerController(HotelManagerService service, PlatformAdminGuard platformAdminGuard) {
        this.service = service;
        this.platformAdminGuard = platformAdminGuard;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelManagerResponse create(@Valid @RequestBody CreateHotelManagerRequest request) {
        platformAdminGuard.requirePlatformAdmin();
        return service.create(request);
    }

    @GetMapping
    public List<HotelManagerResponse> list() {
        platformAdminGuard.requirePlatformAdmin();
        return service.list();
    }

    @PutMapping("/{id}/hotels")
    public HotelManagerResponse replaceHotels(@PathVariable UUID id, @Valid @RequestBody HotelIdsRequest request) {
        platformAdminGuard.requirePlatformAdmin();
        return service.replaceHotels(id, request);
    }

    @PatchMapping("/{id}/status")
    public HotelManagerResponse updateStatus(@PathVariable UUID id, @Valid @RequestBody StatusUpdateRequest request) {
        platformAdminGuard.requirePlatformAdmin();
        return service.updateStatus(id, request.active());
    }

    @PostMapping("/{id}/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@PathVariable UUID id, @Valid @RequestBody ResetPasswordRequest request) {
        platformAdminGuard.requirePlatformAdmin();
        service.resetPassword(id, request.password());
    }
}
