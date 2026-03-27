package com.springboot.useradminrolebased.Controller;

import com.springboot.useradminrolebased.DTO.RegisterResponseDTO;
import com.springboot.useradminrolebased.DTO.UpdateRequestDTO;
import com.springboot.useradminrolebased.DTO.UpdateResponseDTO;
import com.springboot.useradminrolebased.Response.ApiResponse;
import com.springboot.useradminrolebased.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<RegisterResponseDTO>>getMyData(
            @PathVariable Long userId
    ){
        ApiResponse<RegisterResponseDTO>response=userService.getMyData(userId);

        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(response.getStatusCode())
        );
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UpdateResponseDTO>>updateMyData(
            @PathVariable Long userId,@Valid @RequestBody UpdateRequestDTO dto
            ){
        ApiResponse<UpdateResponseDTO>response=userService.updateMyData(userId,dto);

        return new ResponseEntity<>(
                response,HttpStatus.valueOf(response.getStatusCode())
        );
    }
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<RegisterResponseDTO>>deleteMyId(
            @PathVariable Long userId
    ){
        ApiResponse<RegisterResponseDTO>response=userService.deleteMyData(userId);

        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(response.getStatusCode())
        );
    }
}
