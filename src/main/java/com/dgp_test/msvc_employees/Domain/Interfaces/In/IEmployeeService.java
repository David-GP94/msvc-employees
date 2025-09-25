package com.dgp_test.msvc_employees.Domain.Interfaces.In;

import com.dgp_test.msvc_employees.Domain.Entities.Employee;

import java.util.List;

public interface IEmployeeService {

    Employee create(Employee employee);

    List<Employee> findAll();

    Employee findById(Long id);

    Employee update(Long id,Employee employee);

    void delete(Long id);

    List<Employee> findByName(String name);
}
