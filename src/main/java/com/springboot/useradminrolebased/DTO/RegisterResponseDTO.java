package com.springboot.useradminrolebased.DTO;

import com.springboot.useradminrolebased.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponseDTO {

    private Long userId;
    private String userName;
    private String userEmail;
    private Role role;
}
