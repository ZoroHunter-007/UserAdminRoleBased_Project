package com.springboot.useradminrolebased.Handler;

import com.springboot.useradminrolebased.Exception.EmailExistsException;
import com.springboot.useradminrolebased.Exception.InvalidCredentialsException;
import com.springboot.useradminrolebased.Exception.UnauthorizedException;
import com.springboot.useradminrolebased.Exception.UserNotFoundException;
import com.springboot.useradminrolebased.Response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailExistsException.class)
    public ResponseEntity<ApiResponse<Object>>handleEmailAlreadyException(EmailExistsException e){
        ApiResponse<Object>response=new ApiResponse<>(
                409,
                "Error",
                "Email Already Exists! Please use another Email",
                e.getMessage()
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(response.getStatusCode())
        );
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>>handleUserNotFound(UserNotFoundException ue){
        ApiResponse<Object>response=new ApiResponse<>(
                404,
                "Error",
                "User Not Found! Please try Again",
                ue.getMessage()
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(response.getStatusCode())
        );
    }
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Object>>handleUnauthorizedException(UnauthorizedException e){
        ApiResponse<Object>response=new ApiResponse<>(
                403,
                "Error",
                "You have no permission to access this Page",
                e.getMessage()
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.valueOf(response.getStatusCode())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>>handleMethodInvalid(MethodArgumentNotValidException me){

        List<String> errors= me.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(er -> er.getField()+" : "+ er.getDefaultMessage())
                .toList();
        return ResponseEntity.badRequest()
                .body(
                        new ApiResponse<>(
                                400,
                                "Error",
                                "Invalid Argument Passed",
                                me.getMessage()
                        )
                );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiResponse<Object>> handleInvalidCredentials(InvalidCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ApiResponse<>(
                        401,
                        "Error",
                        ex.getMessage(),
                        null
                ));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>>handleInternalError(Exception e){
        return ResponseEntity.internalServerError()
                .body(
                        new ApiResponse<>(
                                500,
                                "Error",
                                "Something went wrong! Try Again",
                                e.getMessage()
                        )
                );
    }

}
