package com.rohov.tagsoft.controller.facade;

import com.rohov.tagsoft.controller.facade.dto.UserDto;

public interface UserFacade {

    UserDto update(Long userId, UserDto userDto);

    UserDto patch(Long userId, UserDto userDto);

    void delete(Long userId);
}
