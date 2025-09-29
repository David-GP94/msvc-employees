package com.dgp_test.msvc_employees.Domain.Interfaces.Out.JpaRepositories;

import com.dgp_test.msvc_employees.Domain.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
