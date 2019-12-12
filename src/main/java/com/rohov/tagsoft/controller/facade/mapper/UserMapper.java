package com.rohov.tagsoft.controller.facade.mapper;

import com.rohov.tagsoft.controller.facade.dto.UserDto;
import com.rohov.tagsoft.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .country(user.getCountry())
                .email(user.getEmail())
                .password(null)
                .createdDate(user.getCreatedDate())
                .updatedDate(user.getUpdatedDate())
                .states(user.getStates())
                .province(user.getProvince())
                .city(user.getCity())
                .build();
    }

    public User toEntity(UserDto userDto) {
        User user = new User();

        user.setName(userDto.getName());
        user.setCountry(userDto.getCountry());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setStates(userDto.getStates());
        user.setProvince(userDto.getProvince());
        user.setCity(userDto.getCity());

        return user;
    }

}
