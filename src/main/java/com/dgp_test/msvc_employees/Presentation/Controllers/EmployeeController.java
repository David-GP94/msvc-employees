package com.dgp_test.msvc_employees.Presentation.Controllers;

import com.dgp_test.msvc_employees.Application.Dtos.Employees.BatchCreationResultDto;
import com.dgp_test.msvc_employees.Application.Dtos.Employees.CreateEmployeeRequestDto;
import com.dgp_test.msvc_employees.Application.Dtos.Employees.EmployeeResponseDto;
import com.dgp_test.msvc_employees.Application.Dtos.Employees.UpdateEmployeeRequestDto;
import com.dgp_test.msvc_employees.Domain.Entities.Employee;
import com.dgp_test.msvc_employees.Domain.Interfaces.In.IEmployeeService;
import com.dgp_test.msvc_employees.Domain.Utils.GeneralMethods;
import com.dgp_test.msvc_employees.Presentation.Responses.GlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Employees", description = "API for managing employees")
public class EmployeeController {
     private final IEmployeeService _employeeService;
     private final ModelMapper modelMapper;

    @Operation(
            summary = "Get all employees",
            description = "Retrieves a list of all registered employees. Requires JWT authentication with USER or ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Employees retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized. JWT token not provided or invalid",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. Insufficient role permissions",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No employees found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    @GetMapping
    public ResponseEntity<GlobalResponse<List<EmployeeResponseDto>>> getAllEmployees(){
        List<Employee> employees = _employeeService.findAll();

        List<EmployeeResponseDto> employeeResponse = employees.stream()
                .map(employee -> modelMapper.map(employee, EmployeeResponseDto.class))
                .toList();

        GlobalResponse<List<EmployeeResponseDto>> response = new GlobalResponse<>(true, GeneralMethods.GetGeneralMessage(2), employeeResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Get employee by ID",
            description = "Retrieves the details of an employee by their ID. Requires JWT authentication with USER or ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized. JWT token not provided or invalid",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. Insufficient role permissions",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<GlobalResponse<EmployeeResponseDto>> getEmployeeById(@PathVariable Long id){
        Employee employee = _employeeService.findById(id);
        GlobalResponse<EmployeeResponseDto> response = new GlobalResponse<>(true, GeneralMethods.GetGeneralMessage(3), modelMapper.map(employee, EmployeeResponseDto.class));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Create employees",
            description = "Creates a list of employees. The birthDate field must be in dd-MM-yyyy format. Requires JWT authentication with ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Employees created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request. Invalid input data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized. JWT token not provided or invalid",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. Requires ADMIN role",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict. No employees were created",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    @PostMapping("/create")
    public ResponseEntity<GlobalResponse<BatchCreationResultDto>> createEmployees(@RequestBody @Valid List<CreateEmployeeRequestDto> request){
        Boolean resourceIsInserted = false;
        BatchCreationResultDto result = _employeeService.create(request);
        String message = result.getSuccessful().isEmpty() ? GeneralMethods.GetGeneralMessage(12) : GeneralMethods.GetGeneralMessage(1);
        GlobalResponse<BatchCreationResultDto>  response = new GlobalResponse<BatchCreationResultDto>(!result.getSuccessful().isEmpty(), message, result);
        return new ResponseEntity<>(response, result.getSuccessful().isEmpty() ? HttpStatus.CONFLICT : HttpStatus.CREATED );
    }

    @Operation(
            summary = "Update employee",
            description = "Updates an employee by their ID. The birthDate field must be in dd-MM-yyyy format. Requires JWT authentication with ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request. Invalid input data",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized. JWT token not provided or invalid",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. Requires ADMIN role",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<GlobalResponse<EmployeeResponseDto>> update(@PathVariable Long id, @Valid @RequestBody UpdateEmployeeRequestDto request) {
        Employee employee = modelMapper.map(request, Employee.class);
        Employee employeeUpdated = _employeeService.update(id, employee);
        GlobalResponse<EmployeeResponseDto> response = new GlobalResponse<>(true, GeneralMethods.GetGeneralMessage(4), modelMapper.map(employeeUpdated, EmployeeResponseDto.class));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Delete employee",
            description = "Deletes an employee by their ID. Requires JWT authentication with ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Employee deleted successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized. JWT token not provided or invalid",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. Requires ADMIN role",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Employee not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            )
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<GlobalResponse<Void>> delete(@PathVariable Long id){
        _employeeService.delete(id);
        GlobalResponse<Void> response = new GlobalResponse<>(true, GeneralMethods.GetGeneralMessage(5), null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(
            summary = "Search employees by name",
            description = "Searches for employees by their name. Returns a list of employees matching the provided name. Requires JWT authentication with USER or ADMIN role.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Employees retrieved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized. JWT token not provided or invalid",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden. Insufficient role permissions",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No employees found with the provided name",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = GlobalResponse.class))
            )
    })
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
