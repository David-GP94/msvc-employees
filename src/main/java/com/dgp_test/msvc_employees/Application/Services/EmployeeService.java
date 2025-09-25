package com.dgp_test.msvc_employees.Application.Services;

import com.dgp_test.msvc_employees.Domain.Entities.Employee;
import com.dgp_test.msvc_employees.Domain.Interfaces.In.IEmployeeService;
import com.dgp_test.msvc_employees.Domain.Interfaces.Out.IEmployeePersistence;
import com.dgp_test.msvc_employees.Infrastructure.Config.CustomExceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    final private IEmployeePersistence _employeePersistence;

    @Override
    public Employee create(Employee employee) {
       return _employeePersistence.save(employee);
    }

    @Override
    public List<Employee> findAll() {
        return _employeePersistence.findAllActive();
    }

    @Override
    public Employee findById(Long id) {
        return _employeePersistence.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The Employee with Id: " + id + "not found"));
    }

    @Override
    public Employee update(Long id, Employee employee) {
        Employee existing = findById(id);
        existing.setFirstName(employee.getFirstName());
        existing.setSecondName(employee.getSecondName());
        existing.setLastNamePaternal(employee.getLastNamePaternal());
        existing.setLastNameMaternal(employee.getLastNameMaternal());
        existing.setAge(employee.getAge());
        existing.setSex(employee.getSex());
        existing.setBirthDate(employee.getBirthDate());
        existing.setPosition(employee.getPosition());
        existing.setIsActive(employee.getIsActive());
        return _employeePersistence.save(existing);
    }

    @Override
    public void delete(Long id) {
        Employee existing = findById(id);
        existing.setIsActive(false);
        _employeePersistence.save(existing);
    }

    @Override
    public List<Employee> findByName(String name) {
         List<Employee> employees = _employeePersistence.findByPartialName(name);
         if (employees.isEmpty()){
             throw  new ResourceNotFoundException("employee not found with parameter: " + name);
         }
         return employees;
    }
}
