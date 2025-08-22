// com.greenfox.backend.security.JwtAuthFilter
package com.greenfox.backend.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String auth = request.getHeader("Authorization");
        if (auth != null && auth.startsWith("Bearer ")) {
            String token = auth.substring(7);
            Claims claims = jwtUtil.extractClaims(token);

            String phone = claims.getSubject();
            Long userId = claims.get("userId", Long.class);
            String role = claims.get("role", String.class); // CLIENT/PARTNER/ADMIN

            var authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
            var principal = UserPrincipal.builder()
                    .id(userId)
                    .username(phone)
                    .password(null)
                    .authorities(authorities)
                    .build();

            var authentication = new UsernamePasswordAuthenticationToken(principal, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
