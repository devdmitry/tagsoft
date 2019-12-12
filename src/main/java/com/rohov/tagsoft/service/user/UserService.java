package com.rohov.tagsoft.service.user;

import com.rohov.tagsoft.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User create(User user);

    User update(Long userId, User user);

    User patch(Long userId, User user);

    void delete(Long userId);
}
