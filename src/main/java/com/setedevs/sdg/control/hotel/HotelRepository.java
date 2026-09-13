package com.setedevs.sdg.control.hotel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HotelRepository extends JpaRepository<Hotel, UUID> {

    Optional<Hotel> findBySlug(String slug);
}
