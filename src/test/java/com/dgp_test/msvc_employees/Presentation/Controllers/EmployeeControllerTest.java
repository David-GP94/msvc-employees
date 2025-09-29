package com.dgp_test.msvc_employees.Presentation.Controllers;

import com.dgp_test.msvc_employees.Application.Dtos.Employees.BatchCreationResultDto;
import com.dgp_test.msvc_employees.Application.Dtos.Employees.CreateEmployeeRequestDto;
import com.dgp_test.msvc_employees.Application.Dtos.Employees.EmployeeResponseDto;
import com.dgp_test.msvc_employees.Application.Dtos.Employees.UpdateEmployeeRequestDto;
import com.dgp_test.msvc_employees.Domain.Entities.Employee;
import com.dgp_test.msvc_employees.Domain.Interfaces.In.IEmployeeService;
import com.dgp_test.msvc_employees.Infrastructure.Config.CustomExceptions.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEmployeeService employeeService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper; // Para serializar payloads

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getAllEmployees_success() throws Exception {
        // Preparar mocks
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastNamePaternal("Doe");
        List<Employee> employees = List.of(employee);

        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(1L);
        dto.setFirstName("John");
        dto.setLastNamePaternal("Doe");

        when(employeeService.findAll()).thenReturn(employees);
        when(modelMapper.map(employee, EmployeeResponseDto.class)).thenReturn(dto);

        // Ejecutar y verificar
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Successfully Obtained Resources."))
                .andExpect(jsonPath("$.data[0].id").value(1L))
                .andExpect(jsonPath("$.data[0].firstName").value("John"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getAllEmployees_resourceNotFound() throws Exception {
        when(employeeService.findAll()).thenThrow(new ResourceNotFoundException("Resource Not Found."));

        // Ejecutar y verificar
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Resource Not Found."));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getEmployeeById_success() throws Exception {
        // Preparar mocks
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastNamePaternal("Doe");

        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(1L);
        dto.setFirstName("John");
        dto.setLastNamePaternal("Doe");

        when(employeeService.findById(1L)).thenReturn(employee);
        when(modelMapper.map(employee, EmployeeResponseDto.class)).thenReturn(dto);

        // Ejecutar y verificar
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Resource obtained correctly."))
                .andExpect(jsonPath("$.data.id").value(1L))
                .andExpect(jsonPath("$.data.firstName").value("John"));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void getEmployeeById_resourceNotFound() throws Exception {
        when(employeeService.findById(1L)).thenThrow(new ResourceNotFoundException("Resource Not Found."));

        // Ejecutar y verificar
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Resource Not Found."));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void createEmployees_success() throws Exception {
        // Preparar payload
        CreateEmployeeRequestDto requestDto = CreateEmployeeRequestDto.builder()
                .firstName("John")
                .lastNamePaternal("Doe")
                .position("Developer")
                .birthDate(LocalDate.of(1994, 2, 25))
                .build();
        List<CreateEmployeeRequestDto> dtos = List.of(requestDto);

        // Preparar respuesta
        EmployeeResponseDto responseDto = EmployeeResponseDto.builder()
                .id(1L)
                .firstName("John")
                .lastNamePaternal("Doe")
                .position("Developer")
                .birthDate(LocalDate.of(1994, 2, 25))
                .build();
        BatchCreationResultDto result = new BatchCreationResultDto();
        result.setSuccessful(List.of(responseDto));
        result.setFailed(new ArrayList<>());

        when(employeeService.create(dtos)).thenReturn(result);

        // Ejecutar y verificar
        mockMvc.perform(post("/api/employees/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dtos)))
                .andExpect(status().isCreated()) // Cambiado de isOk() a isCreated()
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Successfully created resource."))
                .andExpect(jsonPath("$.data.successful[0].id").value(1L))
                .andExpect(jsonPath("$.data.successful[0].firstName").value("John"))
                .andExpect(jsonPath("$.data.successful[0].lastNamePaternal").value("Doe"))
                .andExpect(jsonPath("$.data.successful[0].position").value("Developer"))
                .andExpect(jsonPath("$.data.successful[0].birthDate").value("25-02-1994")); // Verifica formato dd-MM-yyyy
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void updateEmployee_success() throws Exception {
        // Preparar payload
        UpdateEmployeeRequestDto requestDto = UpdateEmployeeRequestDto.builder()
                .firstName("Jane")
                .position("Senior Developer")
                .build();

        // Preparar entidad Employee para el servicio
        Employee employeeInput = new Employee();
        employeeInput.setFirstName("Jane");
        employeeInput.setPosition("Senior Developer");

        // Preparar entidad Employee devuelta por el servicio (con valores preservados)
        Employee employeeOutput = new Employee();
        employeeOutput.setId(1L);
        employeeOutput.setFirstName("Jane");
        employeeOutput.setLastNamePaternal("Doe"); // Valor preservado
        employeeOutput.setPosition("Senior Developer");
        employeeOutput.setBirthDate(LocalDate.of(1994, 2, 25)); // Valor preservado

        // Preparar respuesta DTO
        EmployeeResponseDto responseDto = EmployeeResponseDto.builder()
                .id(1L)
                .firstName("Jane")
                .lastNamePaternal("Doe")
                .position("Senior Developer")
                .birthDate(LocalDate.of(1994, 2, 25))
                .build();

        // Configurar mocks
        when(modelMapper.map(requestDto, Employee.class)).thenReturn(employeeInput);
        when(employeeService.update(1L, employeeInput)).thenReturn(employeeOutput);
        when(modelMapper.map(employeeOutput, EmployeeResponseDto.class)).thenReturn(responseDto);

        // Ejecutar y verificar
        mockMvc.perform(put("/api/employees/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Resource updated successfully."))
                .andExpect(jsonPath("$.data.firstName").value("Jane"))
                .andExpect(jsonPath("$.data.position").value("Senior Developer"))
                .andExpect(jsonPath("$.data.lastNamePaternal").value("Doe"))
                .andExpect(jsonPath("$.data.birthDate").value("25-02-1994"));
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void deleteEmployee_success() throws Exception {
        // No se devuelve cuerpo, así que solo verificamos status 200
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/employees/delete/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Resource deleted successfully."));
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    void searchEmployee_success() throws Exception {
        // Preparar mocks
        Employee employee = new Employee();
        employee.setId(1L);
        employee.setFirstName("John");
        employee.setLastNamePaternal("Doe");

        EmployeeResponseDto dto = new EmployeeResponseDto();
        dto.setId(1L);
        dto.setFirstName("John");
        dto.setLastNamePaternal("Doe");

        when(employeeService.findByName("John")).thenReturn(List.of(employee));
        when(modelMapper.map(employee, EmployeeResponseDto.class)).thenReturn(dto);

        // Ejecutar y verificar
        mockMvc.perform(MockMvcRequestBuilders.get("/api/employees/search")
                        .param("name", "John")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Successfully Obtained Resources."))
                .andExpect(jsonPath("$.data[0].id").value(1L));
    }
}
