package com.rohov.tagsoft.service.auth.impl;

import com.rohov.tagsoft.controller.request.UserCredential;
import com.rohov.tagsoft.entity.User;
import com.rohov.tagsoft.exception.ErrorMessages;
import com.rohov.tagsoft.exception.UserAlreadyExistException;
import com.rohov.tagsoft.exception.UserCredentialException;
import com.rohov.tagsoft.security.TokenProvider;
import com.rohov.tagsoft.service.auth.AuthService;
import com.rohov.tagsoft.service.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {

    AuthenticationManager authenticationManager;
    TokenProvider tokenProvider;

    UserService userService;

    @Override
    public String signIn(UserCredential credential, HttpServletResponse response) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credential.getEmail(), credential.getPassword()));

            return tokenProvider.createToken((User) auth.getPrincipal());
        } catch (AuthenticationException ex) {
            throw new UserCredentialException(ErrorMessages.WRONG_CREDENTIAL);
        }
    }

    @Override
    public User signUp(User user) {
        try {
            UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
            final String errorMsg = String.format(ErrorMessages.USER_WITH_EMAIL_ALREADY_EXIST, userDetails.getUsername());
            throw new UserAlreadyExistException(errorMsg);
        } catch (UsernameNotFoundException ex) {
            return userService.create(user);
        }
    }
}
