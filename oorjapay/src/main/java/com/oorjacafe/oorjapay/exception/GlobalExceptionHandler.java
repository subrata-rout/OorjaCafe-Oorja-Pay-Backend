package com.oorjacafe.oorjapay.exception;


import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static org.apache.logging.log4j.ThreadContext.put;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String>handleValidation(MethodArgumentNotValidException ex){
        Map<String, String> errors=new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error->errors.put(error.getField(),error.getDefaultMessage())
                );
        return errors;

    }

}