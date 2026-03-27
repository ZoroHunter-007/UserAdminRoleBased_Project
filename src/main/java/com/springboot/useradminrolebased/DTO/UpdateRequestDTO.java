package com.springboot.useradminrolebased.DTO;

import com.springboot.useradminrolebased.Entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateRequestDTO {

    private String userName;

    @Email
    private String userEmail;

    @Size(min = 6,message = "Password must be at least 6 characters")
    private String password;

    private Role role;
}
