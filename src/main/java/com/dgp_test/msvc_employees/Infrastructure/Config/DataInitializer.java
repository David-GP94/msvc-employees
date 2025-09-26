package com.dgp_test.msvc_employees.Infrastructure.Config;

import com.dgp_test.msvc_employees.Domain.Entities.Role;
import com.dgp_test.msvc_employees.Domain.Entities.User;
import com.dgp_test.msvc_employees.Domain.Interfaces.Out.Persistence.IRolePersistence;
import com.dgp_test.msvc_employees.Domain.Interfaces.Out.Persistence.IUserPersisntece;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initData(IUserPersisntece userRepository, IRolePersistence roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Insertar roles si no existen
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_USER")));

            // Insertar usuario admin si no existe
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setFirstName("Admin");
                admin.setLastName("Admin");
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("adminpass"));
                admin.setRoles(Set.of(adminRole));
                userRepository.save(admin);
            }

            // Insertar usuario normal si no existe
            if (userRepository.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setFirstName("User");
                user.setLastName("User");
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("userpass"));
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
            }
        };
    }
}
