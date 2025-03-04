package com.practice.jwt.provider;

import com.practice.domain.user.User;
import com.practice.domain.user.repository.UserRepository;
import com.practice.jwt.authentication.AccessTokenAuthenticationToken;
import com.practice.jwt.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@RequiredArgsConstructor
public class AccessTokenAuthenticationProvider implements AuthenticationProvider {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
        throws AuthenticationException {
        log.info("AccessTokenAuthenticationProvider called");
        AccessTokenAuthenticationToken authenticationToken = (AccessTokenAuthenticationToken) authentication;

        UserDetails userDetails = authenticationToken.getAccessToken()
            .filter(jwtService::isTokenValid)
            .flatMap(accessToken -> jwtService.extractEmail(accessToken))
            .flatMap(email -> userRepository.findByEmail(email))
            .map(this::buildUserDetails)
            .orElse(null);

        return new AccessTokenAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AccessTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private UserDetails buildUserDetails(User user) {
        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
            .username(user.getEmail())
            .password("") // 이미 jwt 검증이 완료된, 즉 인증된 사용자이므로 패스워드는 설정하지 않음.
            .roles(user.getRole().name())
            .build();
        return userDetailsUser;
    }
}
