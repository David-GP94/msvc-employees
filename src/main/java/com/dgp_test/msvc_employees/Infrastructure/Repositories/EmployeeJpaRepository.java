package com.dgp_test.msvc_employees.Infrastructure.Repositories;

import com.dgp_test.msvc_employees.Domain.Entities.Employee;
import com.dgp_test.msvc_employees.Domain.Interfaces.Out.IEmployeePersistence;
import com.dgp_test.msvc_employees.Domain.Interfaces.Out.IEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmployeeJpaRepository implements IEmployeePersistence {

    private final IEmployeeRepository _employeeRepository;

    @Override
    public List<Employee> saveAll(List<Employee> employees) {
        return _employeeRepository.saveAll(employees);
    }

    @Override
    public List<Employee> findAllActive() {
        return _employeeRepository.findAllByIsActiveTrue();
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return _employeeRepository.findById(id);
    }

    @Override
    public List<Employee> findByPartialName(String name) {
        return _employeeRepository.findByNameContaining(name);

    }

    @Override
    public Employee save(Employee employee) {
        return _employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> findByNameAndAge(String name, String lastNamePaternal, String lastNameMaternal, Integer age) {
        return _employeeRepository.findByNameAndAge(name, lastNamePaternal, lastNameMaternal, age);
    }
}
