package com.springboot.useradminrolebased.Service;


import com.springboot.useradminrolebased.DTO.RegisterResponseDTO;
import com.springboot.useradminrolebased.DTO.UpdateRequestDTO;
import com.springboot.useradminrolebased.DTO.UpdateResponseDTO;
import com.springboot.useradminrolebased.Entity.Users;
import com.springboot.useradminrolebased.Exception.UnauthorizedException;
import com.springboot.useradminrolebased.Exception.UserNotFoundException;
import com.springboot.useradminrolebased.Mapper.UserMapper;
import com.springboot.useradminrolebased.Repository.UserRepository;
import com.springboot.useradminrolebased.Response.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserMapper userMapper;
    public ApiResponse<RegisterResponseDTO>getMyData(Long userId){
        String currentUsername= SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Users users=userRepository.findById(userId)
                .orElseThrow(()->{
                    log.error("User ID Not Found:{}",userId);
                    return new UserNotFoundException("User "+userId+" ID not found");
                });
        if(!users.getUserEmail().equals(currentUsername)){
            log.error("You're not Authorized");
            throw new UnauthorizedException("You're not Authorized");
        }
        return new ApiResponse<>(
                200,
                "Success",
                "User "+userId+" ID Successfully Fetched",
                userMapper.convertResponse(users)
        );
    }

    public ApiResponse<UpdateResponseDTO> updateMyData(Long useId, UpdateRequestDTO dto) {
        String currentUser = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Users user = userRepository.findById(useId)
                .orElseThrow(() -> {
                    log.error("User not Found: {}", useId);
                    return new UserNotFoundException("User " + useId + " ID not found");
                });
        if (!user.getUserEmail().equals(currentUser)) {
            throw new UnauthorizedException("You're not Authorized");
        }

        userMapper.mapUpdateFields(user, dto, encoder);
        Users updateUser = userRepository.save(user);

        log.info("User {} updated successfully", useId);
        return new ApiResponse<>(
                200,
                "Success",
                "User " + useId + " ID Successfully Updated",
                userMapper.convertUpdate(updateUser)
        );
    }
    public ApiResponse<RegisterResponseDTO>deleteMyData(Long userId){
        String currentUsername=SecurityContextHolder.getContext()
                .getAuthentication().getName();
        Users user=userRepository.findById(userId)
                .orElseThrow(()->
                        new UserNotFoundException("User "+userId+" not found")
                );
        if (!user.getUserEmail().equals(currentUsername)){
            throw new UnauthorizedException("You're not Authorized");
        }
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
