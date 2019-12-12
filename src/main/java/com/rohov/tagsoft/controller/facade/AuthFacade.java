package com.rohov.tagsoft.controller.facade;

import com.rohov.tagsoft.controller.facade.dto.UserDto;
import com.rohov.tagsoft.controller.request.UserCredential;

import javax.servlet.http.HttpServletResponse;

public interface AuthFacade {

    String signIn(UserCredential credential, HttpServletResponse response);

    UserDto signUp(UserDto userDto);
}
