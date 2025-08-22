
package com.greenfox.backend.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter @Builder
@AllArgsConstructor
public class UserPrincipal implements UserDetails {
    private final Long id;
    private final String username; // phone
    private final String password; // может быть null в фильтре
    private final Collection<? extends GrantedAuthority> authorities;

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
