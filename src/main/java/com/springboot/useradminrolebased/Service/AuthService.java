package com.springboot.useradminrolebased.Service;

import com.springboot.useradminrolebased.DTO.LoginRequestDTO;
import com.springboot.useradminrolebased.DTO.LoginResponseDTO;
import com.springboot.useradminrolebased.DTO.RegisterRequestDTO;
import com.springboot.useradminrolebased.DTO.RegisterResponseDTO;
import com.springboot.useradminrolebased.Entity.Users;
import com.springboot.useradminrolebased.Exception.InvalidCredentialsException;
import com.springboot.useradminrolebased.Exception.UserNotFoundException;
import com.springboot.useradminrolebased.Repository.UserRepository;
import com.springboot.useradminrolebased.Response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    public ApiResponse<RegisterResponseDTO>registerUser(RegisterRequestDTO dto){
        log.info("Registering user with email:{}",dto.getUserEmail());
        if(userRepository.findByUserEmail(dto.getUserEmail()).isPresent()){
            log.error("User Email Not Found:{}",dto.getUserEmail());
            throw  new UserNotFoundException("User not found");

        }
        Users users =new Users();
        users.setUserName(dto.getUserName());
        users.setUserEmail(dto.getUserEmail());
        users.setPassword(encoder.encode(dto.getPassword()));
        users.setRole(dto.getRole());

        Users savedUsers =userRepository.save(users);
        return new ApiResponse<>(
                201,
                "Created",
                "User Register Successfully",
                convertResponse(savedUsers)
        );
    }
    public ApiResponse<LoginResponseDTO>loginUser(LoginRequestDTO dto){
        log.info("Login user with email:{}",dto.getUserEmail());
        Users users =userRepository.findByUserEmail(dto.getUserEmail())
                .orElseThrow(()->{
                    log.error("User Not found:{}",dto.getUserEmail());
                    return new UserNotFoundException("User not Found");
                });
        if(!encoder.matches(dto.getPassword(), users.getPassword())){
            log.error("Invalid Email and Password");
            throw new InvalidCredentialsException("Invalid Email and Password");
        }
        return new ApiResponse<>(
                200,
                "Success",
                "Login Successfully",
                convertLogin(users)
        );
    }

    private RegisterResponseDTO convertResponse(Users users){
        return new RegisterResponseDTO(
                users.getUserId(),
                users.getUserName(),
                users.getUserEmail(),
                users.getRole()
        );
    }

    private LoginResponseDTO convertLogin(Users users){
        return new LoginResponseDTO(
                users.getUserId(),
                users.getUserName(),
                users.getUserEmail(),
                users.getRole()
        );
    }
}
