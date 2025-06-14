package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.CustomerRequestDTO;
import com.progra3.cafeteria_api.model.dto.CustomerResponseDTO;
import com.progra3.cafeteria_api.model.dto.CustomerUpdateDTO;
import com.progra3.cafeteria_api.service.port.ICustomerService;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@Tag(name = "Customers", description = "Operations related to customer management")
public class CustomerController {

    private final ICustomerService customerService;

    @Operation(summary = "Create a new customer", description = "Registers a new customer in the system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Customer created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid customer data", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping
    public ResponseEntity<CustomerResponseDTO> createCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Customer data to create",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CustomerRequestDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "firstName": "John",
                                      "lastName": "Doe",
                                      "email": "john.doe@example.com",
                                      "phone": "123456789"
                                    }
                                    """)
                    )
            )
            @Valid @RequestBody CustomerRequestDTO dto) {
        CustomerResponseDTO responseDTO = customerService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/customers/" + responseDTO.id()))
                .body(responseDTO);
    }

    @Operation(summary = "Get all customers", description = "Returns a list of all registered customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer list retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CustomerResponseDTO.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @Operation(summary = "Get a customer by ID", description = "Retrieves a customer by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(
            @Parameter(description = "ID of the customer to retrieve") @PathVariable Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }

    @Operation(summary = "Update a customer", description = "Updates customer data partially (e.g., contact information)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CustomerResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid update data", content = @Content)
    })
    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> updateCustomer(
            @Parameter(description = "ID of the customer to update") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Customer fields to update",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CustomerUpdateDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "email": "new.email@example.com",
                                      "phone": "987654321"
                                    }
                                    """)
                    )
            )
            @Valid @RequestBody CustomerUpdateDTO dto) {
        return ResponseEntity.ok(customerService.update(id, dto));
    }

    @Operation(summary = "Delete a customer", description = "Deletes a customer by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Customer not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID of the customer to delete") @PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}