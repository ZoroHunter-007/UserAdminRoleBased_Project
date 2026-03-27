package com.springboot.useradminrolebased.Service;

import com.springboot.useradminrolebased.DTO.RegisterResponseDTO;
import com.springboot.useradminrolebased.DTO.UpdateRequestDTO;
import com.springboot.useradminrolebased.DTO.UpdateResponseDTO;
import com.springboot.useradminrolebased.Entity.Users;
import com.springboot.useradminrolebased.Exception.UserNotFoundException;
import com.springboot.useradminrolebased.Mapper.UserMapper;
import com.springboot.useradminrolebased.Repository.UserRepository;
import com.springboot.useradminrolebased.Response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    public ApiResponse<List<RegisterResponseDTO>>getAllUsers(){
        List<RegisterResponseDTO>list=userRepository.findAll()
                .stream()
                .map(this::convertResponse)
                .toList();
        return new ApiResponse<>(
                200,
                "Success",
                "All Users Successfully Fetched",
                list
        );
    }
    public ApiResponse<RegisterResponseDTO>getUserById(Long userId){

        Users users=userRepository.findById(userId)
                .orElseThrow(()->{
                    log.error("User ID Not Found:{}",userId);
                    return new UserNotFoundException("User "+userId+" ID not found");
                });

        return new ApiResponse<>(
                200,
                "Success",
                "User "+userId+" ID Successfully Fetched",
                userMapper.convertResponse(users)
        );
    }

    public ApiResponse<UpdateResponseDTO> updateUserById(Long useId, UpdateRequestDTO dto) {
        Users users = userRepository.findById(useId)
                .orElseThrow(() -> {
                    log.error("User not Found: {}", useId);
                    return new UserNotFoundException("User " + useId + " ID not found");
                });

        userMapper.mapUpdateFields(users, dto, encoder);
        Users updateUser = userRepository.save(users);

        log.info("User {} updated successfully by Admin", useId);
        return new ApiResponse<>(
                200,
                "Success",
                "User " + useId + " ID Successfully Updated",
                userMapper.convertUpdate(updateUser)
        );
    }

    public ApiResponse<RegisterResponseDTO>deleteUserById(Long userId){
        Users user=userRepository.findById(userId)
                .orElseThrow(()->
                        new UserNotFoundException("User "+userId+" not found")
                );

        userRepository.delete(user);

        return new ApiResponse<>(
                200,
                "Success",
                "User "+userId+" ID Deleted Successfully",
                null
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
    private UpdateResponseDTO convertUpdate(Users users){
        return new UpdateResponseDTO(
                users.getUserId(),
                users.getUserName(),
                users.getUserEmail(),
                users.getRole()
        );
    }



}
