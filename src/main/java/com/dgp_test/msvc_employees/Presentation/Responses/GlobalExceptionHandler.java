package com.dgp_test.msvc_employees.Presentation.Responses;

import com.dgp_test.msvc_employees.Domain.Utils.GeneralMethods;
import com.dgp_test.msvc_employees.Infrastructure.Config.CustomExceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GlobalResponse<String>> generalExceptionHandler(Exception ex){
        GlobalResponse<String> response = new GlobalResponse<>(false, GeneralMethods.GetGeneralMessage(7), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<GlobalResponse<String>> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
        GlobalResponse<String> response = new GlobalResponse<>(false, GeneralMethods.GetGeneralMessage(6), ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalResponse<Map<String, String>>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.put(error.getField(), error.getDefaultMessage());
        }
        GlobalResponse<Map<String, String>> response = new GlobalResponse<>(false, GeneralMethods.GetGeneralMessage(9), errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
