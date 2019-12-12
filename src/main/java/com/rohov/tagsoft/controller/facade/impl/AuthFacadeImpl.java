package com.rohov.tagsoft.controller.facade.impl;

import com.rohov.tagsoft.controller.facade.AuthFacade;
import com.rohov.tagsoft.controller.facade.dto.UserDto;
import com.rohov.tagsoft.controller.facade.mapper.UserMapper;
import com.rohov.tagsoft.controller.request.UserCredential;
import com.rohov.tagsoft.entity.User;
import com.rohov.tagsoft.service.auth.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthFacadeImpl implements AuthFacade {

    AuthService authService;
    UserMapper userMapper;

    @Override
    public String signIn(UserCredential credential, HttpServletResponse response) {
        return authService.signIn(credential, response);
    }

    @Override
    public UserDto signUp(UserDto userDto) {
        User user = authService.signUp(userMapper.toEntity(userDto));

        return userMapper.toDto(user);
    }
}
