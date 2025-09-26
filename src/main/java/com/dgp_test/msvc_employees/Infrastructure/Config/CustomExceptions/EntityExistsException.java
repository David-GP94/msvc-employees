package com.dgp_test.msvc_employees.Infrastructure.Config.CustomExceptions;

public class EntityExistsException extends RuntimeException{
    public EntityExistsException(String message) {
        super(message);
    }
}
