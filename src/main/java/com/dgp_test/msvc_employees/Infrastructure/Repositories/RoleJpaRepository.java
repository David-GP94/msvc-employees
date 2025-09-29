package com.dgp_test.msvc_employees.Infrastructure.Repositories;

import com.dgp_test.msvc_employees.Domain.Entities.Role;
import com.dgp_test.msvc_employees.Domain.Interfaces.Out.JpaRepositories.IRoleRepository;
import com.dgp_test.msvc_employees.Domain.Interfaces.Out.Persistence.IRolePersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleJpaRepository implements IRolePersistence {

    final private IRoleRepository _roleRepository;

    @Override
    public Role save(Role role) {
         return _roleRepository.save(role);
    }

    @Override
    public Optional<Role> findByName(String name) {
        return _roleRepository.findByName(name);
    }
}
