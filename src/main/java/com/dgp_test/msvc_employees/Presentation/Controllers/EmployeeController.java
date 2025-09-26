package com.dgp_test.msvc_employees.Presentation.Controllers;

import com.dgp_test.msvc_employees.Application.Dtos.Employees.BatchCreationResultDto;
import com.dgp_test.msvc_employees.Application.Dtos.Employees.CreateEmployeeRequestDto;
import com.dgp_test.msvc_employees.Application.Dtos.Employees.EmployeeResponseDto;
import com.dgp_test.msvc_employees.Application.Dtos.Employees.UpdateEmployeeRequestDto;
import com.dgp_test.msvc_employees.Domain.Entities.Employee;
import com.dgp_test.msvc_employees.Domain.Interfaces.In.IEmployeeService;
import com.dgp_test.msvc_employees.Domain.Utils.GeneralMethods;
import com.dgp_test.msvc_employees.Presentation.Responses.GlobalResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/employees")
@RequiredArgsConstructor
public class EmployeeController {
     private final IEmployeeService _employeeService;
     private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<GlobalResponse<List<EmployeeResponseDto>>> getAllEmployees(){
        List<Employee> employees = _employeeService.findAll();

        List<EmployeeResponseDto> employeeResponse = employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponseDto.class))
                .toList();

        GlobalResponse<List<EmployeeResponseDto>> response = new GlobalResponse<>(true, GeneralMethods.GetGeneralMessage(2), employeeResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<EmployeeResponseDto>> getEmployeeById(@PathVariable Long id){
        Employee employee = _employeeService.findById(id);
        GlobalResponse<EmployeeResponseDto> response = new GlobalResponse<>(true, GeneralMethods.GetGeneralMessage(3), modelMapper.map(employee, EmployeeResponseDto.class));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<GlobalResponse<BatchCreationResultDto>> createEmployees(@RequestBody @Valid List<CreateEmployeeRequestDto> request){
        Boolean resourceIsInserted = false;
        BatchCreationResultDto result = _employeeService.create(request);
        String message = result.getSuccessful().isEmpty() ? GeneralMethods.GetGeneralMessage(12) : GeneralMethods.GetGeneralMessage(1);
        GlobalResponse<BatchCreationResultDto>  response = new GlobalResponse<BatchCreationResultDto>(!result.getSuccessful().isEmpty(), message, result);
        return new ResponseEntity<>(response, result.getSuccessful().isEmpty() ? HttpStatus.CONFLICT : HttpStatus.CREATED );
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<GlobalResponse<EmployeeResponseDto>> update(@PathVariable Long id, @Valid @RequestBody UpdateEmployeeRequestDto request) {
        Employee employee = modelMapper.map(request, Employee.class);
        Employee employeeUpdated = _employeeService.update(id, employee);
        GlobalResponse<EmployeeResponseDto> response = new GlobalResponse<>(true, GeneralMethods.GetGeneralMessage(4), modelMapper.map(employeeUpdated, EmployeeResponseDto.class));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GlobalResponse<Void>> delete(@PathVariable Long id){
        _employeeService.delete(id);
        GlobalResponse<Void> response = new GlobalResponse<>(true, GeneralMethods.GetGeneralMessage(5), null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<GlobalResponse<List<EmployeeResponseDto>>> searchEmployee(@RequestParam String name) {
        List<Employee> employeesFound = _employeeService.findByName(name);
        List<EmployeeResponseDto> employeeResponseDtos = employeesFound.stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponseDto.class))
                .toList();
        GlobalResponse<List<EmployeeResponseDto>> response = new GlobalResponse<>(true, GeneralMethods.GetGeneralMessage(2), employeeResponseDtos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
