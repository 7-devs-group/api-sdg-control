package com.setedevs.sdg.control.auth;

import java.util.UUID;

public record AuthMembershipSyncRequest(String name, String email, String password, UUID hotelId, String role) {
}
