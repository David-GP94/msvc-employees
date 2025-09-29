package com.dgp_test.msvc_employees.Infrastructure.Repositories;

import com.dgp_test.msvc_employees.Domain.Entities.User;
import com.dgp_test.msvc_employees.Domain.Interfaces.Out.Persistence.IUserPersisntece;
import com.dgp_test.msvc_employees.Domain.Interfaces.Out.JpaRepositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserJpaRepository implements IUserPersisntece {

    final private IUserRepository _userRepository;
    @Override
    public Optional<User> findByUsername(String username) {
        return _userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        return _userRepository.save(user);
    }
}
