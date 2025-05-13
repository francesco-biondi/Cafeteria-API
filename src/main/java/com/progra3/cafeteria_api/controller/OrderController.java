package com.progra3.cafeteria_api.controller;

import com.progra3.cafeteria_api.model.dto.*;
import com.progra3.cafeteria_api.model.enums.OrderStatus;
import com.progra3.cafeteria_api.service.impl.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
@Tag(name = "Orders", description = "Operations related to orders")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Create a new order", description = "Creates a new order with optional customer and employee IDs. The order starts in ACTIVE state.")
    @ApiResponse(responseCode = "201", description = "Order successfully created")
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@RequestBody @Valid OrderRequestDTO dto) {
        OrderResponseDTO responseDTO = orderService.create(dto);
        return ResponseEntity
                .created(URI.create("/api/orders/" + responseDTO.id()))
                .body(responseDTO);
    }


    @Operation(summary = "Get all orders", description = "Retrieves a list of all orders in the system")
    @ApiResponse(responseCode = "200", description = "List of orders returned successfully")
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAll() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @Operation(summary = "Get an order by ID", description = "Retrieves the details of a specific order based on its ID")
    @ApiResponse(responseCode = "200", description = "Order found and returned")
    @ApiResponse(responseCode = "404", description = "Order not found")
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(orderService.getById(id));
    }

    @Operation(summary = "Update an order", description = "Updates basic fields of an existing order")
    @ApiResponse(responseCode = "200", description = "Order updated successfully")
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable @NotNull Long id, @RequestBody @Valid OrderRequestDTO dto) {
        return ResponseEntity.ok(orderService.update(id, dto));
    }

    @Operation(summary = "Update the discount of an order", description = "Updates the discount value of an order")
    @ApiResponse(responseCode = "200", description = "Discount updated successfully")
    @PatchMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> updateDiscount(
            @PathVariable @NotNull Long id,
            @RequestParam @Min(0) @Max(100) Integer discount) {

        return ResponseEntity.ok(orderService.updateDiscount(id, discount));
    }

    @Operation(summary = "Get items from an order", description = "Returns all items associated with a given order ID")
    @ApiResponse(responseCode = "200", description = "List of items returned successfully")
    @GetMapping("/{id}/items")
    public ResponseEntity<List<ItemResponseDTO>> getItems(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(orderService.getItems(id));
    }

    @Operation(summary = "Add a new item to an order", description = "Adds a new item to the order. The item will be included in the total and subtotal calculations.")
    @ApiResponse(responseCode = "201", description = "Item successfully added to order")
    @PostMapping("/{id}/items")
    public ResponseEntity<ItemResponseDTO> addItem(
            @PathVariable @NotNull Long id,
            @RequestBody @Valid ItemRequestDTO dto) {
        ItemResponseDTO itemResponseDTO = orderService.addItem(id, dto);
        return ResponseEntity
                .created(URI.create("/api/orders/{id}/items/" + itemResponseDTO.id()))
                .body(itemResponseDTO);
    }

    @Operation(summary = "Update an item in an order", description = "Modifies the comment and quantity of an existing item within an order")
    @ApiResponse(responseCode = "200", description = "Item updated successfully")
    @PutMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<ItemResponseDTO> updateItem(
            @PathVariable @NotNull Long orderId,
            @PathVariable @NotNull Long itemId,
            @RequestBody @Valid ItemRequestDTO dto) {
        return ResponseEntity.ok(orderService.updateItem(orderId, itemId, dto));
    }

    @Operation(summary = "Remove an item from an order", description = "Performs a logical delete of the item from the order. It will no longer affect the total.")
    @ApiResponse(responseCode = "200", description = "Item marked as deleted")
    @DeleteMapping("/{orderId}/items/{itemId}")
    public ResponseEntity<ItemResponseDTO> removeItem(
            @PathVariable @NotNull Long orderId,
            @PathVariable @NotNull Long itemId) {
        return ResponseEntity.ok(orderService.removeItem(orderId, itemId));
    }

    @Operation(summary = "Split an order", description = "Splits an order into two separate orders based on the provided items")
    @ApiResponse(responseCode = "200", description = "Order split successfully")
    @PatchMapping("/{orderId}/split")
    public ResponseEntity<List<OrderResponseDTO>> splitOrder(
            @PathVariable @NotNull Long orderId,
            @RequestBody @Valid OrderSplitRequestDTO dto) {
        return ResponseEntity.ok(orderService.splitOrder(orderId, dto));
    }

    @Operation(summary = "Finalize an order", description = "Marks the order as finalized, preventing further changes")
    @ApiResponse(responseCode = "200", description = "Order finalized successfully")
    @PatchMapping("/{id}/finalize")
    public ResponseEntity<OrderResponseDTO> finalizeOrder(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(orderService.updateStatus(id, OrderStatus.FINALIZED));
    }

    @Operation(summary = "Mark an order as billed", description = "Updates the order status to BILLED, indicating the bill was printed")
    @ApiResponse(responseCode = "200", description = "Order status updated to BILLED")
    @PatchMapping("/{id}/bill")
    public ResponseEntity<OrderResponseDTO> billOrder(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(orderService.updateStatus(id, OrderStatus.BILLED));
    }

    @Operation(summary = "Cancel an order", description = "Marks the order as CANCELED and excludes it from further operations")
    @ApiResponse(responseCode = "200", description = "Order canceled successfully")
    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<OrderResponseDTO> cancelOrder(@PathVariable @NotNull Long id) {
        return ResponseEntity.ok(orderService.updateStatus(id, OrderStatus.CANCELED));
    }

}
