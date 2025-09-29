package com.dgp_test.msvc_employees.Domain.Interfaces.Out.Persistence;

import com.dgp_test.msvc_employees.Domain.Entities.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeePersistence {
    List<Employee> saveAll(List<Employee> employees);

    List<Employee> findAllActive();

    Optional<Employee> findById(Long id);

    List<Employee> findByPartialName(String name);
    Employee save(Employee employee);
    Optional<Employee> findByNameAndAge(String name, String lastNamePaternal, String lastNameMaternal, Integer age);
}
