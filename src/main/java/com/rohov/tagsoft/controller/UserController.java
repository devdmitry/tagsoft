package com.rohov.tagsoft.controller;

import com.rohov.tagsoft.controller.facade.UserFacade;
import com.rohov.tagsoft.controller.facade.dto.UserDto;
import io.swagger.annotations.ApiImplicitParam;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

    UserFacade userFacade;

    @ApiImplicitParam(name = "Authorization", dataType = "String", paramType = "header", required = true)
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> update(@PathVariable Long userId, @Valid @RequestBody UserDto userDto) {
        UserDto response = userFacade.update(userId, userDto);
        return ResponseEntity.ok(response);
    }

    @ApiImplicitParam(name = "Authorization", dataType = "String", paramType = "header", required = true)
    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> patch(@PathVariable Long userId, @RequestBody UserDto userDto) {
        UserDto response = userFacade.patch(userId, userDto);
        return ResponseEntity.ok(response);
    }

    @ApiImplicitParam(name = "Authorization", dataType = "String", paramType = "header", required = true)
    @DeleteMapping("/{userId}")
    public ResponseEntity delete(@PathVariable Long userId) {
        userFacade.delete(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
