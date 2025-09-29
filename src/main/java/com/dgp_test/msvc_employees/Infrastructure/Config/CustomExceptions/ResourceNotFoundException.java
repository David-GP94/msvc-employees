package com.dgp_test.msvc_employees.Infrastructure.Config.CustomExceptions;


public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
