package com.dgp_test.msvc_employees.Domain.Interfaces.Out.JpaRepositories;

import com.dgp_test.msvc_employees.Domain.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role save(Role role);
    Optional<Role> findByName(String name);
}
