package com.dgp_test.msvc_employees.Domain.Interfaces.Out.Persistence;

import com.dgp_test.msvc_employees.Domain.Entities.User;

import java.util.Optional;

public interface IUserPersisntece {
    Optional<User> findByUsername(String username);
    User save(User user);
}
