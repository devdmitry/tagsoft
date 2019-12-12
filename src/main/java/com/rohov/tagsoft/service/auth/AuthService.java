package com.rohov.tagsoft.service.auth;

import com.rohov.tagsoft.controller.request.UserCredential;
import com.rohov.tagsoft.entity.User;

import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    String signIn(UserCredential credential, HttpServletResponse response);

    User signUp(User user);
}
