package com.gple.backend.global.security.details;

import com.gple.backend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;

@AllArgsConstructor
public class AuthDetails implements UserDetails {
    private User user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(role ->
            new SimpleGrantedAuthority(role.getPermission())
        ).toList();
    }

    @Override
    @Deprecated
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }
}
