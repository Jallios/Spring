package com.example.pr5_4.Models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER, ADMIN;
    @Override
    public String getAuthority()
    {
        return name();
    }
}
