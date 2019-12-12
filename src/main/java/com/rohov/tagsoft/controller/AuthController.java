package com.rohov.tagsoft.controller;

import com.rohov.tagsoft.controller.facade.AuthFacade;
import com.rohov.tagsoft.controller.facade.dto.UserDto;
import com.rohov.tagsoft.controller.request.UserCredential;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    AuthFacade authFacade;

    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@Valid @RequestBody UserCredential credential,
                                         HttpServletResponse response) {
        return ResponseEntity.ok(authFacade.signIn(credential, response));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(authFacade.signUp(userDto));
    }
}
