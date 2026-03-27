package com.springboot.useradminrolebased.Controller;

import com.springboot.useradminrolebased.DTO.RegisterResponseDTO;
import com.springboot.useradminrolebased.DTO.UpdateRequestDTO;
import com.springboot.useradminrolebased.DTO.UpdateResponseDTO;
import com.springboot.useradminrolebased.Response.ApiResponse;
import com.springboot.useradminrolebased.Service.AdminService;
import com.springboot.useradminrolebased.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<List<RegisterResponseDTO>>>getAllUsers(){
        ApiResponse<List<RegisterResponseDTO>>response=adminService.getAllUsers();

        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(response.getStatusCode())
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<RegisterResponseDTO>>getMyData(
            @PathVariable Long userId
    ){
        ApiResponse<RegisterResponseDTO>response=adminService.getUserById(userId);

        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(response.getStatusCode())
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<UpdateResponseDTO>>updateMyData(
            @PathVariable Long userId,@Valid @RequestBody UpdateRequestDTO dto
    ){
        ApiResponse<UpdateResponseDTO>response=adminService.updateUserById(userId,dto);

        return new ResponseEntity<>(
                response,HttpStatus.valueOf(response.getStatusCode())
        );
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<RegisterResponseDTO>>deleteMyId(
            @PathVariable Long userId
    ){
        ApiResponse<RegisterResponseDTO>response=adminService.deleteUserById(userId);

        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(response.getStatusCode())
        );
    }
}
