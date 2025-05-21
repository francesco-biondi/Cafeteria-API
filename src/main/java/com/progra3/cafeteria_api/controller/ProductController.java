package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.ProductGroupResponseDTO;
import com.progra3.cafeteria_api.model.dto.ProductRequestDTO;
import com.progra3.cafeteria_api.model.dto.ProductResponseDTO;
import com.progra3.cafeteria_api.service.impl.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Tag(name = "Products", description = "Operations related to product management")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all products", description = "Returns a list of all registered products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product list retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping
    public List<ProductResponseDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get a product by ID", description = "Retrieves a product by its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(
            @Parameter(description = "ID of the product to retrieve") @PathVariable @NotNull Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Create a new product", description = "Registers a new product in the system")
    @ApiResponse(responseCode = "201", description = "Product created successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ProductResponseDTO.class)))
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Product data to create",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProductRequestDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "name": "Smartphone",
                                      "description": "Latest model with 128GB storage",
                                      "price": 699.99,
                                      "stock": 50
                                    }
                                    """)
                    )
            )
            @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        return new ResponseEntity<>(productService.createProduct(productRequestDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing product", description = "Modifies the details of an existing product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @Parameter(description = "ID of the product to update") @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated product data",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProductRequestDTO.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "name": "Smartphone Pro",
                                      "description": "Updated model with better features",
                                      "price": 799.99,
                                      "stock": 30
                                    }
                                    """)
                    )
            )
            @RequestBody @Valid ProductRequestDTO productRequestDTO) {
        return new ResponseEntity<>(productService.updateProduct(productRequestDTO), HttpStatus.OK);
    }

    @Operation(summary = "Delete a product", description = "Deletes a product by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> deleteProduct(
            @Parameter(description = "ID of the product to delete") @PathVariable @NotNull Long id) {
        ProductResponseDTO response = productService.deleteProduct(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/groups/{groupId}")
    public ResponseEntity<ProductResponseDTO> assignGroupToProduct(
            @PathVariable Long id,
            @PathVariable Long groupId) {
        ProductResponseDTO response = productService.assignGroupToProduct(id, groupId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/groups/{groupId}")
    public ResponseEntity<ProductResponseDTO> removeGroupFromProduct(
            @PathVariable Long id,
            @PathVariable Long groupId) {
        ProductResponseDTO response = productService.removeGroupFromProduct(id, groupId);
        return ResponseEntity.ok(response);
    }
}