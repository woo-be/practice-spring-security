package com.practice.jwt.authentication;

import java.util.Collection;
import java.util.Optional;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AccessTokenAuthenticationToken extends AbstractAuthenticationToken {

    private String accessToken;
    private Object principal;

    public AccessTokenAuthenticationToken(String accessToken) {
        super(null);
        this.accessToken = accessToken;
        this.setAuthenticated(false);
    }

    public AccessTokenAuthenticationToken(Object principal, String accessToken, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.accessToken = accessToken;
        this.principal = principal;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return accessToken;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

    public Optional<String> getAccessToken() {
        return Optional.ofNullable(accessToken);
    }
}
