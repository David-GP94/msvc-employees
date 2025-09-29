package com.dgp_test.msvc_employees.Domain.Interfaces.Out.JpaRepositories;

import com.dgp_test.msvc_employees.Domain.Entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IEmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e  WHERE " +
            "LOWER(e.firstName) LIKE LOWER(:pattern) OR " +
            "LOWER(e.secondName) LIKE LOWER(:pattern) OR " +
            "LOWER(e.lastNamePaternal) LIKE LOWER(:pattern) OR " +
            "LOWER(e.lastNamePaternal) LIKE LOWER(:pattern)")
    List<Employee> findByNameContaining(@Param("pattern") String pattern);

    List<Employee> findAllByIsActiveTrue();
    Optional<Employee> findByIdAndIsActiveTrue(Long id);

    @Query("SELECT e FROM Employee e WHERE e.firstName = :firstName AND e.lastNamePaternal = :lastNamePaternal AND " +
            "(e.lastNameMaternal = :lastNameMaternal OR (e.lastNameMaternal IS NULL AND :lastNameMaternal IS NULL)) AND e.age = :age")
    Optional<Employee> findByNameAndAge(@Param("firstName") String firstName,
                                        @Param("lastNamePaternal") String lastNamePaternal,
                                        @Param("lastNameMaternal") String lastNameMaternal,
                                        @Param("age") Integer age);
}
