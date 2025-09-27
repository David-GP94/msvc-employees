package com.dgp_test.msvc_employees.Application.Services;

import com.dgp_test.msvc_employees.Application.Dtos.Employees.BatchCreationResultDto;
import com.dgp_test.msvc_employees.Application.Dtos.Employees.CreateEmployeeRequestDto;
import com.dgp_test.msvc_employees.Application.Dtos.Employees.EmployeeResponseDto;
import com.dgp_test.msvc_employees.Application.Dtos.Employees.FailedEmployeeDto;
import com.dgp_test.msvc_employees.Domain.Entities.Employee;
import com.dgp_test.msvc_employees.Domain.Interfaces.In.IEmployeeService;
import com.dgp_test.msvc_employees.Domain.Interfaces.Out.Persistence.IEmployeePersistence;
import com.dgp_test.msvc_employees.Infrastructure.Config.CustomExceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {

    private final IEmployeePersistence _employeePersistence;
    private final ModelMapper modelMapper;
    @Override
    @Transactional
    public BatchCreationResultDto create(List<CreateEmployeeRequestDto> employeeDto) {
        List<EmployeeResponseDto> successful = new ArrayList<>();
        List<FailedEmployeeDto> failed = new ArrayList<>();
        List<Employee> validEmployees = new ArrayList<>();

        for (CreateEmployeeRequestDto dto : employeeDto) {
            try {
                Optional<Employee> existing = _employeePersistence.findByNameAndAge(dto.getFirstName(), dto.getLastNamePaternal(), dto.getLastNameMaternal(), dto.getAge());
                if (existing.isPresent()) {
                    throw new EntityExistsException("Employee duplicated");
                }
                validEmployees.add(modelMapper.map(dto, Employee.class));
            } catch (Exception e) {
                failed.add(new FailedEmployeeDto(modelMapper.map(dto, EmployeeResponseDto.class), e.getMessage()));
            }
        }

        if (!validEmployees.isEmpty()) {
            List<Employee> saved = _employeePersistence.saveAll(validEmployees);
            successful.addAll(saved.stream().map(e -> modelMapper.map(e, EmployeeResponseDto.class)).toList());
        }

        return new BatchCreationResultDto(successful, failed);
    }

    @Override
    public List<Employee> findAll() {
        List<Employee> employees = _employeePersistence.findAllActive();
        if (employees.isEmpty()){
            throw new ResourceNotFoundException("Employees not found");
        }
        return employees;
    }

    @Override
    public Employee findById(Long id) {
        return _employeePersistence.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("The Employee with Id: " + id + " not found"));
    }

    @Override
    @Transactional
    public Employee update(Long id, Employee employee) {
        Employee existing = findById(id);
        existing.setFirstName(Objects.requireNonNullElse(employee.getFirstName(), existing.getFirstName()));
        existing.setSecondName(Objects.requireNonNullElse(employee.getSecondName(), existing.getSecondName()));
        existing.setLastNamePaternal(Objects.requireNonNullElse(employee.getLastNamePaternal(), existing.getLastNamePaternal()));
        existing.setLastNameMaternal(Objects.requireNonNullElse(employee.getLastNameMaternal(), existing.getLastNameMaternal()));
        existing.setAge(Objects.requireNonNullElse(employee.getAge(), existing.getAge()));
        existing.setGender(Objects.requireNonNullElse(employee.getGender(), existing.getGender()));
        existing.setBirthDate(Objects.requireNonNullElse(employee.getBirthDate(), existing.getBirthDate()));
        existing.setPosition(Objects.requireNonNullElse(employee.getPosition(), existing.getPosition()));
        existing.setIsActive(Objects.requireNonNullElse(employee.getIsActive(), existing.getIsActive()));

        return _employeePersistence.save(existing);
    }

    @Override
    @Transactional
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
