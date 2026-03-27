package com.springboot.useradminrolebased.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse <T>{

    private Integer statusCode;
    private String status;
    private String message;
    private T data;
}
