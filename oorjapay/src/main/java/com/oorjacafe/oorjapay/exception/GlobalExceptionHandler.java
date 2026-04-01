package com.oorjacafe.oorjapay.exception;


import com.oorjacafe.oorjapay.response.ApiResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.apache.logging.log4j.ThreadContext.put;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //1. Validation errors(@Valid fails)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Map<String, String>>handleValidation(MethodArgumentNotValidException ex){
        Map<String, String> errors=new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error->errors.put(error.getField(),error.getDefaultMessage())
                );
        return new ApiResponse<>(
                false,
                "Validation failed",
                errors
        );

    }

    //Custom exception
    @ExceptionHandler(UserNotFoundException.class)
    public ApiResponse<String>handleUserNotFound(UserNotFoundException ex){
        return new ApiResponse<>(
                false,
                ex.getMessage(),
                null
        );
    }
   // 2. RunTime exception(Like "User not found")
    @ExceptionHandler(RuntimeException.class)
    public ApiResponse<String>handleRunTimeException(RuntimeException ex){
        return new ApiResponse<>(
                false,
                ex.getMessage(),
                null
        );
    }


    //3.Generic fallback(any unknown error)
    @ExceptionHandler(Exception.class)
    public ApiResponse<String>handleGeneriException(Exception ex){
        return new ApiResponse<>(
                false,
                "Something went wrong",
                null
        );
    }

}