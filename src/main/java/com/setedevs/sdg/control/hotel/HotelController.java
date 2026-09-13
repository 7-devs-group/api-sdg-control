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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/control/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HotelResponse create(@Valid @RequestBody CreateHotelRequest request) {
        return hotelService.create(request);
    }

    @GetMapping
    public List<HotelResponse> list() {
        return hotelService.list();
    }

    @GetMapping("/{id}")
    public HotelResponse get(@PathVariable UUID id) {
        return hotelService.get(id);
    }

    @PostMapping("/{id}/memberships")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createMembership(@PathVariable UUID id, @Valid @RequestBody CreateMembershipRequest request) {
        hotelService.createMembership(id, request);
    }
}
