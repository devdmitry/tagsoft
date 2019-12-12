package com.rohov.tagsoft.controller.facade.dto;

import com.rohov.tagsoft.model.Country;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {

    @NotEmpty(message = "Missing required field: 'name'.")
    String name;

    @NotNull(message = "Missing required field: 'country'.")
    Country country;

    @NotEmpty(message = "Missing required field: 'email'.")
    String email;

    @NotEmpty(message = "Missing required field: 'password'.")
    String password;

    @ApiModelProperty(hidden = true)
    Instant createdDate;

    @ApiModelProperty(hidden = true)
    Instant updatedDate;

    List<String> states = new ArrayList<>();

    String province;

    String city;
}
