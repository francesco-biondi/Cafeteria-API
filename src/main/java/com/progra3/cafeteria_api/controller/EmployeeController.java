package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.EmployeeRequestDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeResponseDTO;
import com.progra3.cafeteria_api.model.dto.EmployeeUpdateDTO;
import com.progra3.cafeteria_api.service.port.IEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.progra3.cafeteria_api.model.enums.Role;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
@Tag(name = "Employees", description = "Operations related to employee management")
public class EmployeeController {

    private final IEmployeeService employeeService;

    @Operation(summary = "Create a new employee", description = "Registers a new employee in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid employee data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Employee data to create",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = EmployeeRequestDTO.class),
                            examples = @ExampleObject(value = """
                                {
                                  "name": "Alice",
                                  "lastName": "Smith",
                                  "dni": "12345678",
                                  "email": "alice.smith@example.com",
                                  "phoneNumber": "541123456789",
                                  "role": "WAITER",
                                  "password": "securePassword"
                                }
                                """)
                    )
            )
            @RequestBody @Valid EmployeeRequestDTO dto) {
        EmployeeResponseDTO response = employeeService.createEmployee(dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all employees", description = "Retrieves a list of all employees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employees retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EmployeeResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @Operation(summary = "Get an employee by ID", description = "Retrieves a specific employee by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(
            @Parameter(description = "ID of the employee to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Operation(summary = "Filter employees", description = "Filters employees based on optional query parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Filtered employees retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EmployeeResponseDTO.class)))),
            @ApiResponse(responseCode = "400", description = "Invalid query parameters", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/filter")
    public List<EmployeeResponseDTO> filterEmployees(
            @Parameter(description = "First name of the employee to filter by (optional)") @RequestParam(required = false) String name,
            @Parameter(description = "Last name of the employee to filter by (optional)") @RequestParam(required = false) String lastName,
            @Parameter(description = "DNI of the employee to filter by (optional)") @RequestParam(required = false) String dni,
            @Parameter(description = "Email of the employee to filter by (optional)") @RequestParam(required = false) String email,
            @Parameter(description = "Phone number of the employee to filter by (optional)") @RequestParam(required = false) String phoneNumber,
            @Parameter(description = "Role of the employee to filter by (optional)") @RequestParam(required = false) Role role,
            @Parameter(description = "Filter by deleted status (optional)") @RequestParam(required = false) Boolean deleted
    ) {
        return employeeService.filterEmployees(name, lastName, dni, email, phoneNumber, role, deleted);
    }

    @Operation(summary = "Update an employee", description = "Partially updates an employee's information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid data", content = @Content),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @Parameter(description = "ID of the employee to update") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Employee fields to update",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = EmployeeUpdateDTO.class),
                            examples = @ExampleObject(value = """
                                {
                                  "name": "John",
                                  "lastName": "Doe",
                                  "dni": "12345678",
                                  "email": "new.email@example.com",
                                  "phoneNumber": "541199887766",
                                  "password": "newSecurePassword",
                                  "role": "ADMIN"
                                }
                                """)
                    )
            )
            @RequestBody @Valid EmployeeUpdateDTO dto) {
        EmployeeResponseDTO response = employeeService.updateEmployee(id, dto);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete an employee", description = "Marks an employee as deleted")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee deleted successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Employee not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> deleteEmployee(
            @Parameter(description = "ID of the employee to delete") @PathVariable @NotNull Long id) {
        EmployeeResponseDTO response = employeeService.deleteEmployee(id);
        return ResponseEntity.ok(response);
    }
}