package com.example.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.security.Permission.CAN_DELETE_RESOURCE;
import static com.example.security.Permission.CAN_VIEW;

public enum Role implements GrantedAuthority {
    ROLE_USER(CAN_VIEW),
    ROLE_MANAGER(CAN_VIEW, CAN_DELETE_RESOURCE);

    private final List<Permission> permissions = new ArrayList<>();

    Role(Permission... permissions) {
        this.permissions.addAll(Arrays.asList(permissions));
    }

    @Override
    public String getAuthority() {
        return this.name();
    }

    public List<GrantedAuthority> getGranularAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        /* Add role */
        authorities.add(this);
        /* Add permissions */
        authorities.addAll(this.permissions);
        return authorities;
    }
}
