package com.samplecompany.teapot.authorization;


import java.util.Collection;
import java.util.Collections;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.samplecompany.teapot.authorization.model.UserDTO;

@Getter
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1947600549263817888L;

    private final UserDTO userDTO;
    private final Collection<GrantedAuthority> authorities = Collections.unmodifiableList(AuthorityUtils.createAuthorityList("USER"));

    @Override
    public String getUsername() {
        return userDTO.getEmail();
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
