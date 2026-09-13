package com.setedevs.sdg.control.hotel;

import com.setedevs.sdg.control.auth.AuthHotelClient;
import com.setedevs.sdg.control.outbox.OutboxEvent;
import com.setedevs.sdg.control.outbox.OutboxEventRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
public class HotelService {

    private final HotelRepository hotelRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final AuthHotelClient authHotelClient;

    public HotelService(
            HotelRepository hotelRepository,
            OutboxEventRepository outboxEventRepository,
            AuthHotelClient authHotelClient
    ) {
        this.hotelRepository = hotelRepository;
        this.outboxEventRepository = outboxEventRepository;
        this.authHotelClient = authHotelClient;
    }

    @Transactional
    public HotelResponse create(CreateHotelRequest request) {
        String slug = request.slug().toLowerCase(Locale.ROOT);
        if (hotelRepository.findBySlug(slug).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Hotel slug already exists");
        }
        Hotel hotel = hotelRepository.save(new Hotel(request.name().trim(), slug));
        String payload = "{\"hotelId\":\"" + hotel.getId() + "\",\"slug\":\"" + hotel.getSlug()
                + "\",\"active\":true}";
        outboxEventRepository.save(new OutboxEvent("HotelCreated", hotel.getId(), payload));
        authHotelClient.synchronizeHotel(hotel);
        return HotelResponse.from(hotel);
    }

    @Transactional(readOnly = true)
    public List<HotelResponse> list() {
        return hotelRepository.findAll().stream().map(HotelResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public HotelResponse get(UUID id) {
        return hotelRepository.findById(id)
                .map(HotelResponse::from)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));
    }

    @Transactional
    public void createMembership(UUID hotelId, CreateMembershipRequest request) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hotel not found"));
        if (hotel.getStatus() != HotelStatus.ACTIVE) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Hotel is inactive");
        }
        authHotelClient.createMembership(
                request.name().trim(), request.email().trim(), request.password(), hotel, request.role().trim()
        );
    }
}
