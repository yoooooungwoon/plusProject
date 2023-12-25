package com.example.plusproject.user;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @Pattern(regexp= "^[a-zA-Z0-9]{3,10}$")
    private String username;
    @Pattern(regexp= "^[a-zA-Z0-9]{4,15}$")
    private String password;

}
