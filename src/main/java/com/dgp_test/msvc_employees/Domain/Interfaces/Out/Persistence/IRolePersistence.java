package com.dgp_test.msvc_employees.Domain.Interfaces.Out.Persistence;

import com.dgp_test.msvc_employees.Domain.Entities.Role;

import java.util.Optional;

public interface IRolePersistence {
    Role save(Role role);
    Optional<Role> findByName(String name);
}
