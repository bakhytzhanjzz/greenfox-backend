package com.greenfox.backend.security;

import com.greenfox.backend.entity.Resort;
import com.greenfox.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("sec")
@RequiredArgsConstructor
public class SecurityExpressions {

    private final ResortRepository resortRepository;
    private final BookingRepository bookingRepository;

    public boolean isSelf(Authentication auth, Long userId) {
        UserPrincipal p = (UserPrincipal) auth.getPrincipal();
        return p.getId().equals(userId);
    }

    public boolean canManageResort(Authentication auth, Long resortId) {
        UserPrincipal p = (UserPrincipal) auth.getPrincipal();
        Resort r = resortRepository.findById(resortId).orElse(null);
        return r != null && r.getOwner() != null && r.getOwner().getId().equals(p.getId());
    }

    public boolean canManageBooking(Authentication auth, Long bookingId) {
        UserPrincipal p = (UserPrincipal) auth.getPrincipal();
        return bookingRepository.findById(bookingId)
                .map(b -> b.getUser().getId().equals(p.getId())
                        || b.getResort().getOwner().getId().equals(p.getId()))
                .orElse(false);
    }
}
