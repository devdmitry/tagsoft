package com.rohov.tagsoft.controller.facade.impl;

import com.rohov.tagsoft.controller.facade.UserFacade;
import com.rohov.tagsoft.controller.facade.dto.UserDto;
import com.rohov.tagsoft.controller.facade.mapper.UserMapper;
import com.rohov.tagsoft.entity.User;
import com.rohov.tagsoft.service.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserFacadeImpl implements UserFacade {

    UserService userService;
    UserMapper userMapper;

    @Override
    public UserDto update(Long userId, UserDto userDto) {
        User user = userService.update(userId, userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto patch(Long userId, UserDto userDto) {
        User user = userService.patch(userId, userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }

    @Override
    public void delete(Long userId) {
        userService.delete(userId);
    }
}
