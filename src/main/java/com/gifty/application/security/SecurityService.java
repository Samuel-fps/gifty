package com.gifty.application.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityService {

    private final AuthenticationContext authenticationContext;

    public SecurityService(AuthenticationContext authenticationContext) {
        this.authenticationContext = authenticationContext;
    }

    public UserDetails getAuthenticatedUser() {
        Optional<UserDetails> userDetailsOptional = authenticationContext.getAuthenticatedUser(UserDetails.class);
        if (userDetailsOptional.isPresent()) {
            return userDetailsOptional.get();
        } else {
            throw new RuntimeException("There is no authenticated user");
        }
    }

    public void logout() {
        authenticationContext.logout();
    }
}
