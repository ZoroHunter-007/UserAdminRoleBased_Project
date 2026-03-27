package com.springboot.useradminrolebased.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequestDTO {

    @Email
    @NotBlank(message = "User Email can't be Blank")
    private String userEmail;

    @NotBlank(message = "User Password can't be Blank")
    @Size(min = 6,message = "Password must be at least 6 characters")
    private String password;
}
