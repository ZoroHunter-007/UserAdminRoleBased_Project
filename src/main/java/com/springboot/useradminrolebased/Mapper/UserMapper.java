package com.springboot.useradminrolebased.Mapper;

import com.springboot.useradminrolebased.DTO.RegisterResponseDTO;
import com.springboot.useradminrolebased.DTO.UpdateRequestDTO;
import com.springboot.useradminrolebased.DTO.UpdateResponseDTO;
import com.springboot.useradminrolebased.Entity.Users;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public void mapUpdateFields(Users user, UpdateRequestDTO dto, PasswordEncoder encoder) {
        if (dto.getUserName() != null) {
            user.setUserName(dto.getUserName());
        }
        if (dto.getUserEmail() != null) {
            user.setUserEmail(dto.getUserEmail());
        }
        if (dto.getPassword() != null) {
            user.setPassword(encoder.encode(dto.getPassword()));
        }
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }
    }

    public RegisterResponseDTO convertResponse(Users users) {
        return new RegisterResponseDTO(
                users.getUserId(),
                users.getUserName(),
                users.getUserEmail(),
                users.getRole()
        );
    }

    public UpdateResponseDTO convertUpdate(Users users) {
        return new UpdateResponseDTO(
                users.getUserId(),
                users.getUserName(),
                users.getUserEmail(),
                users.getRole()
        );
    }
}