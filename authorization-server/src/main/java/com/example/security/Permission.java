package com.example.security;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    CAN_VIEW, CAN_DELETE_RESOURCE;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
