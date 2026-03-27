package com.springboot.useradminrolebased.Controller;

import com.springboot.useradminrolebased.DTO.LoginRequestDTO;
import com.springboot.useradminrolebased.DTO.LoginResponseDTO;
import com.springboot.useradminrolebased.DTO.RegisterRequestDTO;
import com.springboot.useradminrolebased.DTO.RegisterResponseDTO;
import com.springboot.useradminrolebased.Response.ApiResponse;
import com.springboot.useradminrolebased.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
   private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegisterResponseDTO>>registerUser(
            @Valid @RequestBody RegisterRequestDTO dto
            ){
        ApiResponse<RegisterResponseDTO>response=authService.registerUser(dto);

        return  new ResponseEntity<>(
                response,
                HttpStatus.valueOf(response.getStatusCode())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>>loginUser(
            @Valid @RequestBody LoginRequestDTO dto
            ){
        ApiResponse<LoginResponseDTO>response=authService.loginUser(dto);

        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(response.getStatusCode())
        );
    }
}
