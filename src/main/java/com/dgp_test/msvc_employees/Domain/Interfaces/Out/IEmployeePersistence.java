package com.dgp_test.msvc_employees.Domain.Interfaces.Out;

import com.dgp_test.msvc_employees.Domain.Entities.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeePersistence {
    Employee save(Employee employee);

    List<Employee> findAllActive();

    Optional<Employee> findById(Long id);

    List<Employee> findByPartialName(String name);
}
