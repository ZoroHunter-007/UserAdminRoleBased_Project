package com.springboot.useradminrolebased.DTO;

import com.springboot.useradminrolebased.Entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDTO {

    @NotBlank(message = "Username can't be Blank")
    private String userName;

    @Email
    @NotBlank(message = "User Email can't be Blank")
    private String userEmail;

    @NotBlank(message = "User Password can't be Blank")
    @Size(min = 6,message = "Password must be at least 6 characters")
    private String password;


    private Role role;
}
