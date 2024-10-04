package org.springboot.taskmanager.security;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;
import java.util.List;

public class UserAuthentication implements Authentication {

    private String token;
    private String username;

    public UserAuthentication(String token) {
        this.token = token;
    }

    public void setUser(String username) {
        this.username = username;
        System.out.println("Username inside UserAuthentication : " + this.username);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public boolean isAuthenticated() {
        return username != null;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if(username != null) {
            username = null;
        }
    }

    @Override
    public String getName() {
        return this.username;
    }
}
