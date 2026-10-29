package com.setedevs.sdg.control.hotel;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.setedevs.sdg.control.security.PlatformAdminGuard;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/control/hotels")
public class HotelController {

    private final HotelService hotelService;
    private final PlatformAdminGuard platformAdminGuard;

    public HotelController(HotelService hotelService, PlatformAdminGuard platformAdminGuard) {
        this.hotelService = hotelService;
        this.platformAdminGuard = platformAdminGuard;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponse create(@Valid @RequestBody CreateHotelRequest request) {
        platformAdminGuard.requirePlatformAdmin();
        return hotelService.create(request);
    }

    @GetMapping
    public List<HotelResponse> list() {
        platformAdminGuard.requirePlatformAdmin();
        return hotelService.list();
    }

    @GetMapping("/{id}")
    public HotelResponse get(@PathVariable UUID id) {
        platformAdminGuard.requirePlatformAdmin();
        return hotelService.get(id);
    }

    @PostMapping("/{id}/memberships")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createMembership(@PathVariable UUID id, @Valid @RequestBody CreateMembershipRequest request) {
        platformAdminGuard.requirePlatformAdmin();
        hotelService.createMembership(id, request);
    }
}
